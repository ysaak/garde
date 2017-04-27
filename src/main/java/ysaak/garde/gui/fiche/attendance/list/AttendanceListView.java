package ysaak.garde.gui.fiche.attendance.list;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ysaak.garde.data.attendance.AttendanceDTO;
import ysaak.garde.data.attendance.AttendancePeriodDTO;
import ysaak.garde.data.attendance.MaintenanceFee;
import ysaak.garde.data.attendance.MealFee;
import ysaak.garde.gui.common.Formatters;
import ysaak.garde.gui.common.UiUtils;
import ysaak.garde.gui.common.actions.ActionType;
import ysaak.garde.gui.common.components.monthselector.MonthSelector;
import ysaak.garde.gui.common.components.monthselector.MonthSelectorListener;
import ysaak.garde.gui.common.components.tablecell.DurationTableCell;
import ysaak.garde.gui.common.components.tablecell.EnumTableCell;
import ysaak.garde.gui.common.components.tablecell.LocalDateTableCell;
import ysaak.garde.gui.common.view.AbstractFormView;
import ysaak.garde.service.translation.I18n;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class AttendanceListView extends AbstractFormView<List<AttendanceDTO>> {
  @FXML
  private Pane rootPane;

  @FXML
  private BorderPane tableActionBar;

  @FXML
  private TableView<AttendanceDTO> attendanceTable;
  
  @FXML
  private TableColumn<AttendanceDTO, LocalDate> dateColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, LocalTime> period1StartColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, LocalTime> period1EndColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, LocalTime> period2StartColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, LocalTime> period2EndColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, LocalTime> period3StartColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, LocalTime> period3EndColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, Duration> durationColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, MealFee> mealFeeColumn;
  
  @FXML
  private TableColumn<AttendanceDTO, MaintenanceFee> maintenanceFeeColumn;
  
  @FXML
  private Label totalHoursLabel;

  private JFXButton addButton;
  
  private ObservableList<AttendanceDTO> data = FXCollections.observableArrayList();

  private LocalDate currentMonth = null;

  private MonthSelector monthSelector = null;

  private MonthSelectorListener monthSelectorListener = null;

  public AttendanceListView() {
    super(I18n.get("attendance.list.title"));
  }

  @Override
  public List<Node> getToolbarComponents() {
    return Collections.singletonList(addButton);
  }

  @FXML
  public void initialize() {
    initializeToolbar();

    // Init table
    attendanceTable.setRowFactory(tableView -> {
      final TableRow<AttendanceDTO> row = new TableRow<>();

      // Build context menu

      MenuItem editItem = new MenuItem(I18n.get("button.edit"), UiUtils.getEditIcon());
      editItem.setOnAction(evt -> onEditAction(row.getItem()));

      MenuItem removeItem = new MenuItem(I18n.get("button.delete"), UiUtils.getDeleteIcon());
      removeItem.setOnAction(evt -> onDeleteAction(row.getItem()));

      final ContextMenu rowMenu = new ContextMenu();
      rowMenu.getItems().addAll(editItem, removeItem);

      // only display context menu for non-null items:
      row.contextMenuProperty().bind(
              Bindings.when(Bindings.isNotNull(row.itemProperty()))
                      .then(rowMenu)
                      .otherwise((ContextMenu)null));
      return row;
    });

    attendanceTable.setItems(data);
    
    dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
    dateColumn.setCellFactory(p -> new LocalDateTableCell<>());
    
    period1StartColumn.setCellValueFactory(c -> getPeriodValue(c.getValue(), 1, true));
    period1EndColumn.setCellValueFactory(c -> getPeriodValue(c.getValue(), 1, false));
    
    period2StartColumn.setCellValueFactory(c -> getPeriodValue(c.getValue(), 2, true));
    period2EndColumn.setCellValueFactory(c -> getPeriodValue(c.getValue(), 2, false));
    
    period3StartColumn.setCellValueFactory(c -> getPeriodValue(c.getValue(), 3, true));
    period3EndColumn.setCellValueFactory(c -> getPeriodValue(c.getValue(), 3, false));
    
    durationColumn.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(calculateDuration(c.getValue())));
    durationColumn.setCellFactory(p -> new DurationTableCell<>());
    
    mealFeeColumn.setCellValueFactory(new PropertyValueFactory<>("mealFee"));
    mealFeeColumn.setCellFactory(p -> new EnumTableCell<>(MealFee.NONE));

    maintenanceFeeColumn.setCellValueFactory(new PropertyValueFactory<>("maintenanceFee"));
    maintenanceFeeColumn.setCellFactory(p -> new EnumTableCell<>());
    
    totalHoursLabel.setText("00:00");
  }

  private void initializeToolbar() {
    // Initialize main toolbar
    addButton = new JFXButton(I18n.get("button.add"), UiUtils.getAddIcon());
    addButton.setOnAction(evt -> fireActionEvent(ActionType.CREATE, null));

    // Initialize date selector
    monthSelector = new MonthSelector();
    monthSelector.setOnNextAction(event -> {
      if (currentMonth != null) {
        fireMonthSelected(currentMonth.plusMonths(1));
      }
    });
    monthSelector.setOnPreviousAction(event -> {
      if (currentMonth != null) {
        fireMonthSelected(currentMonth.minusMonths(1));
      }
    });
    tableActionBar.setLeft(monthSelector);

    // Today button
    JFXButton todayButton = new JFXButton(I18n.get("common.today"));
    todayButton.setOnAction(event -> fireMonthSelected(LocalDate.now().withDayOfMonth(1)));

    tableActionBar.setRight(todayButton);

  }

  @Override
  public void setData(List<AttendanceDTO> data) {
    this.data.setAll(data);
    
    Duration monthDuration = Duration.ofMinutes(0);
    
    for (AttendanceDTO attendance : data) {
      monthDuration = monthDuration.plus(calculateDuration(attendance));
    }
    
    totalHoursLabel.setText(Formatters.formatDuration(monthDuration));
  }
  
  private ReadOnlyObjectProperty<LocalTime> getPeriodValue(AttendanceDTO attendance, int periodId, boolean startTime) {
    LocalTime value = null;
    
    if (attendance != null && attendance.getPeriods() != null && periodId <= attendance.getPeriods().size()) {
      final AttendancePeriodDTO period = attendance.getPeriods().get(periodId - 1);
      if (period != null) {
        value = (startTime) ? period.getStartHour() : period.getEndHour();
      }
    }
    
    return new ReadOnlyObjectWrapper<>(value);
  }
  
  private Duration calculateDuration(AttendanceDTO attendance) {
    Duration duration = null;
    
    if (attendance != null && attendance.getPeriods() != null) {
      for (AttendancePeriodDTO period : attendance.getPeriods()) {
        final Duration shd = Duration.ofSeconds(period.getStartHour().toSecondOfDay());
        final Duration ehd = Duration.ofSeconds(period.getEndHour().toSecondOfDay());
        
        if (duration != null) {
          duration = duration.plus(ehd.minus(shd));
        }
        else {
          duration = ehd.minus(shd);
        }
      }
    }
    
    return duration;
  }
  
  @Override
  public List<AttendanceDTO> getData() {
    return null;
  }

  @Override
  public Node getView() {
    return rootPane;
  }

  public void setCurrentMonth(LocalDate currentMonth) {
    this.currentMonth = currentMonth;
    this.monthSelector.setMonth(currentMonth);
  }

  public void setMonthSelectorListener(MonthSelectorListener monthSelectorListener) {
    this.monthSelectorListener = monthSelectorListener;
  }

  private void fireMonthSelected(LocalDate month) {
    if (monthSelectorListener != null) {
      monthSelectorListener.monthSelected(month);
    }
  }

  private void onEditAction(AttendanceDTO attendance) {
    fireActionEvent(ActionType.UPDATE, Collections.singletonList(attendance));
  }

  private void onDeleteAction(AttendanceDTO attendance) {
    fireActionEvent(ActionType.DELETE, Collections.singletonList(attendance));
  }
}

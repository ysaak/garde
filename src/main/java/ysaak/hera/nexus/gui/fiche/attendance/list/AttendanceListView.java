package ysaak.hera.nexus.gui.fiche.attendance.list;

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
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;
import ysaak.hera.nexus.data.attendance.MaintenanceFee;
import ysaak.hera.nexus.data.attendance.MealFee;
import ysaak.hera.nexus.gui.common.Formatters;
import ysaak.hera.nexus.gui.common.UiUtils;
import ysaak.hera.nexus.gui.common.actions.ActionType;
import ysaak.hera.nexus.gui.common.components.monthselector.MonthSelector;
import ysaak.hera.nexus.gui.common.components.monthselector.MonthSelectorListener;
import ysaak.hera.nexus.gui.common.components.tablecell.DurationTableCell;
import ysaak.hera.nexus.gui.common.components.tablecell.EnumTableCell;
import ysaak.hera.nexus.gui.common.components.tablecell.LocalDateTableCell;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;
import ysaak.hera.nexus.service.translation.I18n;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

public class AttendanceListView extends AbstractFormView<List<Attendance>> {
  @FXML
  private Pane rootPane;

  @FXML
  private BorderPane tableActionBar;

  @FXML
  private TableView<Attendance> attendanceTable;
  
  @FXML
  private TableColumn<Attendance, LocalDate> dateColumn;
  
  @FXML
  private TableColumn<Attendance, LocalTime> period1StartColumn;
  
  @FXML
  private TableColumn<Attendance, LocalTime> period1EndColumn;
  
  @FXML
  private TableColumn<Attendance, LocalTime> period2StartColumn;
  
  @FXML
  private TableColumn<Attendance, LocalTime> period2EndColumn;
  
  @FXML
  private TableColumn<Attendance, LocalTime> period3StartColumn;
  
  @FXML
  private TableColumn<Attendance, LocalTime> period3EndColumn;
  
  @FXML
  private TableColumn<Attendance, Duration> durationColumn;
  
  @FXML
  private TableColumn<Attendance, MealFee> mealFeeColumn;
  
  @FXML
  private TableColumn<Attendance, MaintenanceFee> maintenanceFeeColumn;
  
  @FXML
  private Label totalHoursLabel;

  private JFXButton addButton;
  
  private ObservableList<Attendance> data = FXCollections.observableArrayList();

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
      final TableRow<Attendance> row = new TableRow<>();

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
    mealFeeColumn.setCellFactory(p -> new EnumTableCell<>(MealFee.NO));

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
  public void setData(List<Attendance> data) {
    this.data.setAll(data);
    
    Duration monthDuration = Duration.ofMinutes(0);
    
    for (Attendance attendance : data) {
      monthDuration = monthDuration.plus(calculateDuration(attendance));
    }
    
    totalHoursLabel.setText(Formatters.formatDuration(monthDuration));
  }
  
  private ReadOnlyObjectProperty<LocalTime> getPeriodValue(Attendance attendance, int periodId, boolean startTime) {
    LocalTime value = null;
    
    if (attendance != null && attendance.getPeriods() != null && periodId <= attendance.getPeriods().size()) {
      final AttendancePeriod period = attendance.getPeriods().get(periodId - 1);
      if (period != null) {
        value = (startTime) ? period.getStartHour() : period.getEndHour();
      }
    }
    
    return new ReadOnlyObjectWrapper<>(value);
  }
  
  private Duration calculateDuration(Attendance attendance) {
    Duration duration = null;
    
    if (attendance != null && attendance.getPeriods() != null) {
      for (AttendancePeriod period : attendance.getPeriods()) {
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
  public List<Attendance> getData() {
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

  private void onEditAction(Attendance attendance) {
    fireActionEvent(ActionType.UPDATE, Collections.singletonList(attendance));
  }

  private void onDeleteAction(Attendance attendance) {
    fireActionEvent(ActionType.DELETE, Collections.singletonList(attendance));
  }
}

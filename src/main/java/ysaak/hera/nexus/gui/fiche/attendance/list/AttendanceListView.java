package ysaak.hera.nexus.gui.fiche.attendance.list;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;
import ysaak.hera.nexus.data.attendance.MaintenanceFee;
import ysaak.hera.nexus.data.attendance.MealFee;
import ysaak.hera.nexus.gui.common.Formatters;
import ysaak.hera.nexus.gui.common.components.tablecell.DurationTableCell;
import ysaak.hera.nexus.gui.common.components.tablecell.LocalDateTableCell;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceListView extends AbstractFormView<List<Attendance>> {
  @FXML
  protected Pane rootPane;

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
  
  private ObservableList<Attendance> data = FXCollections.observableArrayList();

  @Override
  public String getTitle() {
    return "Temp title";
  }

  @FXML
  public void initialize() {
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
    maintenanceFeeColumn.setCellValueFactory(new PropertyValueFactory<>("maintenanceFee"));
    
    totalHoursLabel.setText("00:00");
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

}

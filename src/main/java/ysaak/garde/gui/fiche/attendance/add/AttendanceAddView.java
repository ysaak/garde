package ysaak.garde.gui.fiche.attendance.add;

import com.jfoenix.controls.JFXDatePicker;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import ysaak.garde.data.attendance.MaintenanceFee;
import ysaak.garde.gui.common.view.AbstractFormView;
import ysaak.garde.data.attendance.AttendanceDTO;
import ysaak.garde.data.attendance.AttendancePeriodDTO;
import ysaak.garde.data.attendance.MealFee;
import ysaak.garde.gui.common.components.Card;
import ysaak.garde.gui.common.components.IterativeList;
import ysaak.garde.gui.common.components.RadioButtonGroup;
import ysaak.garde.service.translation.I18n;

import java.time.LocalDate;
import java.util.ArrayList;

public class AttendanceAddView extends AbstractFormView<AttendanceDTO> {
  
  private GridPane gridPane;
  
  private IterativeList<AttendancePeriodDTO> periodList;
  
  private RadioButtonGroup<MaintenanceFee> maintenanceFeeGroup;
  
  private RadioButtonGroup<MealFee> mealFeeGroup;

  private JFXDatePicker attendanceDayPicker;

  public AttendanceAddView() {
    super(I18n.get("attendance.edit.title"));
  }

  @Override
  public void initialize() {
    attendanceDayPicker = new JFXDatePicker(LocalDate.now());

    periodList = new IterativeList<>();
    periodList.setMaxLinesCount(3);
    periodList.setLineFactory(p -> loadView(PeriodLineView.class));
    
    maintenanceFeeGroup = new RadioButtonGroup<>();
    maintenanceFeeGroup.setTextFactory(I18n::get);
    maintenanceFeeGroup.setItems(MaintenanceFee.NO, MaintenanceFee.YES);
    
    mealFeeGroup = new RadioButtonGroup<>();
    mealFeeGroup.setTextFactory(I18n::get);
    mealFeeGroup.setItems(MealFee.NONE, MealFee.PARTIAL, MealFee.FULL);

    gridPane = new GridPane();

    gridPane.add(initDayPane(), 0, 0, 2, 1);


    gridPane.add(initFeePane(), 0, 1);
    gridPane.add(initPeriodsListPane(), 1, 1);
    
    ColumnConstraints col1Const = new ColumnConstraints();
    col1Const.setPercentWidth(50.);
    ColumnConstraints col2Const = new ColumnConstraints();
    col2Const.setPercentWidth(50.);
    gridPane.getColumnConstraints().addAll(col1Const, col2Const);
    
    RowConstraints row1Const = new RowConstraints();
    row1Const.setValignment(VPos.TOP);
    
    gridPane.getRowConstraints().addAll(row1Const);
  }

  @Override
  public Node getView() {
    return gridPane;
  }

  private Node initDayPane() {

    final VBox dayPane = new VBox(10.);
    dayPane.getChildren().addAll(
            new Label(I18n.get("attendance.edit.day")),
            attendanceDayPicker
    );

    Card card = new Card(null, dayPane);

    GridPane.setMargin(card.getView(), new Insets(0, 0, 10., 0));

    return card.getView();
  }

  private Node initFeePane() {
    
    final VBox feeLayout = new VBox(10.);
    feeLayout.setPadding(new Insets(10.));
    
    feeLayout.getChildren().addAll(
        new Label(I18n.get("attendance.fee.maintenance")),
        maintenanceFeeGroup.getView(),
        new Label(I18n.get("attendance.fee.meal")),
        mealFeeGroup.getView()
    );
    
    Card feePane = new Card(I18n.get("attendance.edit.fee.title"), feeLayout);
    
    
    GridPane.setMargin(feePane.getView(), new Insets(5.));
    
    return feePane.getView();
  }
  
  private Node initPeriodsListPane() {

    periodList.setPadding(new Insets(10.));
    
    Card periodsPane = new Card(I18n.get("attendance.edit.periods.title"), periodList);
    
    GridPane.setMargin(periodsPane.getView(), new Insets(5.));
    
    return periodsPane.getView();
  }

  @Override
  public void setData(AttendanceDTO data) {
    originalData = data;

    System.err.println(data);

    if (data == null) {
      setTitle(I18n.get("attendance.edit.title"));
      attendanceDayPicker.setValue(LocalDate.now());
      maintenanceFeeGroup.setSelectedItem(MaintenanceFee.NO);
      mealFeeGroup.setSelectedItem(MealFee.NONE);
      periodList.setData(new ArrayList<>());
    }
    else {
      setTitle(I18n.get("attendance.edit.title.update"));
      attendanceDayPicker.setValue(data.getDate());
      maintenanceFeeGroup.setSelectedItem(data.getMaintenanceFee());
      mealFeeGroup.setSelectedItem(data.getMealFee());
      periodList.setData(data.getPeriods());
    }
  }

  @Override
  public AttendanceDTO getData() {
    final AttendanceDTO data = (originalData != null) ? originalData : new AttendanceDTO();
    data.setDate(attendanceDayPicker.getValue());
    data.setMaintenanceFee(maintenanceFeeGroup.getSelectedItem());
    data.setMealFee(mealFeeGroup.getSelectedItem());
    data.setPeriods(periodList.getData());
    return data;
  }
}

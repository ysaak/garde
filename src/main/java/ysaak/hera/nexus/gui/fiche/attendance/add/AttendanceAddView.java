package ysaak.hera.nexus.gui.fiche.attendance.add;

import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;
import ysaak.hera.nexus.data.attendance.MaintenanceFee;
import ysaak.hera.nexus.data.attendance.MealFee;
import ysaak.hera.nexus.gui.common.components.IterativeList;
import ysaak.hera.nexus.gui.common.components.RadioButtonGroup;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;
import ysaak.hera.nexus.gui.common.view.AbstractView;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class AttendanceAddView extends AbstractFormView<Attendance> {
  
  private BorderPane pane;
  
  private IterativeList<AttendancePeriod> periodList;
  
  private RadioButtonGroup<MaintenanceFee> maintenanceFeeGroup;
  
  private RadioButtonGroup<MealFee> mealFeeGroup;

  @Override
  public String getTitle() {
    //FIXME use translation
    return "Ajout d'une présence";
  }

  @Override
  public void initialize() {

    periodList = new IterativeList<>();
    periodList.setMaxLinesCount(3);
    periodList.setLineFactory(p -> new PeriodLineView());
    
    maintenanceFeeGroup = new RadioButtonGroup<>();
    maintenanceFeeGroup.setItems(MaintenanceFee.NO, MaintenanceFee.YES);
    
    mealFeeGroup = new RadioButtonGroup<>();
    mealFeeGroup.setItems(MealFee.NO, MealFee.PARTIAL, MealFee.FULL);
    
    pane = new BorderPane();
    
    
    GridPane gridPane = new GridPane();
    gridPane.add(initFeePane(), 0, 0);
    gridPane.add(initPeriodsListPane(), 1, 0);
    
    ColumnConstraints col1Const = new ColumnConstraints();
    col1Const.setPercentWidth(50.);
    ColumnConstraints col2Const = new ColumnConstraints();
    col2Const.setPercentWidth(50.);
    gridPane.getColumnConstraints().addAll(col1Const, col2Const);
    
    RowConstraints row1Const = new RowConstraints();
    row1Const.setValignment(VPos.TOP);
    
    gridPane.getRowConstraints().addAll(row1Const);
    
    pane.setCenter(gridPane);
    
    
    Label title = new Label("Ajout d'une présence");
    title.getStyleClass().add("module-title");
    pane.setTop(title);
  }

  @Override
  public Node getView() {
    return pane;
  }

  private Node initFeePane() {
    
    final VBox feeLayout = new VBox(10.);
    feeLayout.setPadding(new Insets(10.));
    
    feeLayout.getChildren().addAll(
        new Label("Frais d'entretiens"),
        maintenanceFeeGroup.getView(),
        new Label("Frais de repas"),
        mealFeeGroup.getView()
    );
    
    TitledPane feePane = new TitledPane("Frais", feeLayout);
    feePane.getStyleClass().add("card");
    
    
    GridPane.setMargin(feePane, new Insets(5.));
    
    return feePane;
  }
  
  private Node initPeriodsListPane() {
    AttendancePeriod p1 = new AttendancePeriod();
    p1.setStartHour(LocalTime.of(8, 0));
    p1.setEndHour(LocalTime.of(11, 30));
    
    AttendancePeriod p2 = new AttendancePeriod();
    p2.setStartHour(LocalTime.of(13, 45));
    p2.setEndHour(LocalTime.of(16, 0));
    
    List<AttendancePeriod> testData = Arrays.asList(p1, p2);
    periodList.setData(testData);
    
    periodList.setPadding(new Insets(10.));
    
    TitledPane periodsPane = new TitledPane("Périodes de présence", periodList);
    periodsPane.getStyleClass().add("card");
    
    GridPane.setMargin(periodsPane, new Insets(5.));
    
    return periodsPane;
  }

  @Override
  public void setData(Attendance data) {
  }

  @Override
  public Attendance getData() {
    return null;
  }
}

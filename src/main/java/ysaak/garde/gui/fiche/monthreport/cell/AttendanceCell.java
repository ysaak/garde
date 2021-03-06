package ysaak.garde.gui.fiche.monthreport.cell;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;
import ysaak.garde.business.utils.AttendanceUtils;
import ysaak.garde.data.attendance.AttendanceDTO;
import ysaak.garde.data.attendance.MaintenanceFee;
import ysaak.garde.data.attendance.MealFee;
import ysaak.garde.gui.common.Formatters;
import ysaak.garde.gui.common.view.AbstractView;
import ysaak.garde.service.translation.I18n;

import java.time.Duration;
import java.time.LocalDate;

public class AttendanceCell extends AbstractView<AttendanceDTO> implements MonthReportCell {
  private static final int PERIODS_LAYER = 0;
  private static final int SUMMARY_LAYER = 1;

  private BorderPane mainPane;

  private GridPane periodsPane;
  private VBox summaryPane;

  private Label dayLabel;

  private Label[] periodsLabel = new Label[3];

  private Label summaryLabel;

  private Label mealFeeLabel;
  private FontIcon mealFeeIconLabel;
  private FontIcon maintenanceFeeLabel;

  @Override
  public void initialize() {
    // Period label
    periodsLabel[0] = new Label(" ");
    periodsLabel[1] = new Label(" ");
    periodsLabel[2] = new Label(" ");

    periodsPane = new GridPane();
    periodsPane.addColumn(0, periodsLabel);
    periodsPane.setPadding(new Insets(5));
    periodsPane.setVgap(5);

    // Summary pane
    summaryLabel = new Label(" ");
    summaryLabel.getStyleClass().addAll("month-report-cell", "hours");
    summaryPane = new VBox(summaryLabel);
    summaryPane.setAlignment(Pos.CENTER);

    // Fee pane
    mealFeeLabel = new Label(" ");
    mealFeeIconLabel = new FontIcon(Material.RESTAURANT);
    Tooltip.install(mealFeeIconLabel, new Tooltip(I18n.get("attendance.fee.meal")));
    HBox.setMargin(mealFeeLabel, new Insets(0,0,0,10));
    HBox.setMargin(mealFeeIconLabel, new Insets(0,5,0,0));
    mealFeeIconLabel.setVisible(false);

    maintenanceFeeLabel = new FontIcon(Material.CHILD_CARE);
    Tooltip.install(maintenanceFeeLabel, new Tooltip(I18n.get("attendance.fee.maintenance")));
    HBox.setMargin(maintenanceFeeLabel, new Insets(0,0,0,10));
    maintenanceFeeLabel.setVisible(false);

    HBox feePane = new HBox(mealFeeLabel, mealFeeIconLabel, maintenanceFeeLabel);
    feePane.setAlignment(Pos.CENTER_LEFT);

    // Center pane
    StackPane centerPane = new StackPane();
    centerPane.getChildren().add(PERIODS_LAYER, periodsPane);
    centerPane.getChildren().add(SUMMARY_LAYER, summaryPane);

    periodsPane.setVisible(false);

    centerPane.setOnMouseEntered(evt -> { periodsPane.setVisible(true); summaryPane.setVisible(false); });
    centerPane.setOnMouseExited(evt -> { periodsPane.setVisible(false); summaryPane.setVisible(true); });

    // Day label
    dayLabel = new Label(" ");
    dayLabel.setPadding(new Insets(0, 0, 0, 5));

    // Border pane
    mainPane = new BorderPane(centerPane);
    mainPane.setBottom(feePane);
    mainPane.setTop(dayLabel);
  }

  @Override
  public Node getView() {
    return mainPane;
  }

  public void setDay(LocalDate date) {
    dayLabel.setText(""+date.getDayOfMonth());
  }

  @Override
  public void setData(AttendanceDTO attendance) {

    int nbPeriods = attendance.getPeriods().size();
    for (int i = 0; i < periodsLabel.length; i++) {

      String text = "";
      if (i < nbPeriods) {
        text = Formatters.formatPeriod(attendance.getPeriods().get(i));
      }

      periodsLabel[i].setText(text);
    }

    Duration totalDuration = AttendanceUtils.calculateDuration(attendance);
    this.summaryLabel.setText(Formatters.formatDuration(totalDuration, "h"));

    // Maintenance fee
    if (attendance.getMaintenanceFee() == MaintenanceFee.YES) {
      this.maintenanceFeeLabel.setVisible(true);
    }
    else {
      this.maintenanceFeeLabel.setVisible(false);
    }

    // Meal fee
    if (attendance.getMealFee() == MealFee.FULL) {
      mealFeeLabel.setText("1");
      mealFeeIconLabel.setVisible(true);
    }
    else if (attendance.getMealFee() == MealFee.PARTIAL) {
      mealFeeLabel.setText("½");
      mealFeeIconLabel.setVisible(true);
    }
    else {
      mealFeeLabel.setText(" ");
      mealFeeIconLabel.setVisible(false);
    }
  }

  @Override
  public AttendanceDTO getData() {
    return null;
  }

  @Override
  public void setFaded(boolean faded) {
    Color textColor = faded ? Color.GRAY : Color.BLACK;

    dayLabel.setTextFill(textColor);
    for (Label label : periodsLabel) {
      label.setTextFill(textColor);
    }
    summaryLabel.setTextFill(textColor);
    mealFeeLabel.setTextFill(textColor);
    mealFeeIconLabel.setFill(textColor);
    maintenanceFeeLabel.setFill(textColor);
  }
}

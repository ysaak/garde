package ysaak.hera.nexus.gui.fiche.monthreport.cell;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
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
import javafx.scene.text.Text;
import ysaak.hera.nexus.business.utils.AttendanceUtils;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.attendance.MaintenanceFee;
import ysaak.hera.nexus.data.attendance.MealFee;
import ysaak.hera.nexus.gui.common.Formatters;
import ysaak.hera.nexus.gui.common.view.AbstractView;

import java.time.Duration;
import java.time.LocalDate;

public class AttendanceCell extends AbstractView<Attendance> implements MonthReportCell {
  private static final int PERIODS_LAYER = 0;
  private static final int SUMMARY_LAYER = 1;

  private final BorderPane mainPane;

  private final StackPane centerPane;

  private final GridPane periodsPane;
  private final VBox summaryPane;
  private final HBox feePane;

  private final Label dayLabel;

  private final Label[] periodsLabel = new Label[3];

  private final Label summaryLabel;

  private final Label mealFeeLabel;
  private final Text mealFeeIconLabel;
  private final Text maintenanceFeeLabel;

  public AttendanceCell() {
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
    mealFeeIconLabel = MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.FOOD);
    Tooltip.install(mealFeeIconLabel, new Tooltip("Frais de repas"));
    HBox.setMargin(mealFeeLabel, new Insets(0,0,0,10));
    HBox.setMargin(mealFeeIconLabel, new Insets(0,5,0,0));
    mealFeeIconLabel.setVisible(false);

    maintenanceFeeLabel = MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.WRENCH);
    Tooltip.install(maintenanceFeeLabel, new Tooltip("Frais d'entretient"));
    HBox.setMargin(maintenanceFeeLabel, new Insets(0,0,0,10));
    maintenanceFeeLabel.setVisible(false);

    feePane = new HBox(mealFeeLabel, mealFeeIconLabel, maintenanceFeeLabel);
    feePane.setAlignment(Pos.CENTER_LEFT);

    // Center pane
    centerPane = new StackPane();
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
  public void setData(Attendance attendance) {

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
  public Attendance getData() {
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

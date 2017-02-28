package ysaak.hera.nexus.gui.fiche.monthreport.cell;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ysaak.hera.nexus.data.monthreport.WeekSummary;
import ysaak.hera.nexus.gui.common.Formatters;
import ysaak.hera.nexus.gui.common.view.AbstractView;

public class WeekSummaryCell extends AbstractView<WeekSummary> implements MonthReportCell {

  private final BorderPane mainPane;

  private final Label totalHoursLabel = new Label(" ");
  private final Label complementHoursLabel;
  private final Label increasedHoursLabel;

  public WeekSummaryCell() {

    // Center pane
    totalHoursLabel.getStyleClass().addAll("month-report-cell", "hours");
    VBox thBox = new VBox(totalHoursLabel);
    thBox.setAlignment(Pos.CENTER);
    thBox.setPadding(new Insets(10));


    // --- Supplements
    // Complements hours
    final Label chTextLabel = new Label("Complément. : ");
    complementHoursLabel = new Label(" ");
    complementHoursLabel.getStyleClass().addAll("month-report-cell", "other-hours");
    final HBox chBox = new HBox(chTextLabel, complementHoursLabel);
    chBox.setAlignment(Pos.CENTER_LEFT);
    chBox.setPadding(new Insets(0, 5, 0, 5));

    // Increased hours
    final Label ihTextLabel = new Label("Majorées : ");
    increasedHoursLabel = new Label(" ");
    increasedHoursLabel.getStyleClass().addAll("week-summary-cell", "other-hours");
    final HBox ihBox = new HBox(ihTextLabel, increasedHoursLabel);
    ihBox.setAlignment(Pos.CENTER_LEFT);
    ihBox.setPadding(new Insets(0, 5, 0, 5));

    final GridPane supplementPane = new GridPane();
    supplementPane.addColumn(0, chBox, ihBox);
    supplementPane.setVgap(5);

    // Main pane
    mainPane = new BorderPane(thBox);
    mainPane.setBottom(supplementPane);
    mainPane.getStyleClass().add("week-summary-cell");
  }

  @Override
  public Node getView() {
    return mainPane;
  }

  @Override
  public WeekSummary getData() {
    return null;
  }

  @Override
  public void setData(WeekSummary data) {
    totalHoursLabel.setText(Formatters.formatDuration(data.getTotalHours(), "h"));
    complementHoursLabel.setText(Formatters.formatDuration(data.getComplementHours(), "h"));
    increasedHoursLabel.setText(Formatters.formatDuration(data.getIncreasedHours(), "h"));
  }

  @Override
  public void setFaded(boolean faded) {

  }
}

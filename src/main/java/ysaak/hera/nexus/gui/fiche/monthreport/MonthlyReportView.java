package ysaak.hera.nexus.gui.fiche.monthreport;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import ysaak.hera.nexus.data.Period;
import ysaak.hera.nexus.data.attendance.Attendance;
import ysaak.hera.nexus.data.monthreport.MonthReport;
import ysaak.hera.nexus.gui.common.view.AbstractView;
import ysaak.hera.nexus.gui.fiche.monthreport.cell.AttendanceCell;
import ysaak.hera.nexus.gui.fiche.monthreport.cell.MonthReportCell;
import ysaak.hera.nexus.gui.fiche.monthreport.cell.WeekSummaryCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MonthlyReportView extends AbstractView<MonthReport> {

  private static final int COLUMNS = 7;
  private static final int WEEK_COLUMN = COLUMNS -1;

  private static final DateTimeFormatter CURRENT_MONTH_LABEL_FORMATTER = DateTimeFormatter.ofPattern("MMMM yyyy");

  private VBox pane;

  private GridPane grid;
  private RowConstraints firstRowConstraints;

  private LocalDate currentMonth;
  private Label currentMonthLabel;


  private static final String[] COLUMN_NAMES = new String[] {
    "common.monday",
    "common.tuesday",
    "common.wednesday",
    "common.thursday",
    "common.friday",
    "common.saturday",
    "common.week"
  };

  private final Map<LocalDate, AttendanceCell> attendanceCells = new HashMap<>();
  private final Map<Integer, WeekSummaryCell> weekSummaryCells = new HashMap<>();

  private MonthChangeEvent monthChangeEvent = null;

  public void initialize() {

    currentMonth = LocalDate.now().withDayOfMonth(1);

    grid = new GridPane();
    grid.getStyleClass().add("monthreport-grid");

    for (int i=0; i < COLUMN_NAMES.length; i++) {
      Label label = new Label(translationFacade.get(COLUMN_NAMES[i]));
      VBox pane = new VBox();
      pane.setAlignment(Pos.CENTER);

      pane.getStyleClass().addAll("monthreport-grid-cell", "first-row");
      if (i == 0) {
        pane.getStyleClass().add("first-column");
      }
      if (i == WEEK_COLUMN) {
        pane.getStyleClass().addAll("week-column", "last-column");
      }

      pane.getChildren().add(label);
      grid.add(pane, i, 0);
    }

    firstRowConstraints = new RowConstraints();
    firstRowConstraints.setMinHeight(20);

    for (int i = 1; i <= COLUMNS; i++) {
      ColumnConstraints column = new ColumnConstraints();
      column.setFillWidth(true);
      column.setMinWidth(115);

      if (i < COLUMNS) {
        column.setPercentWidth(16);
      }
      else {
        column.setPercentWidth(20);
      }

      grid.getColumnConstraints().add(column);
    }

    Button prevBtn = new Button("<");
    prevBtn.setOnAction(evt -> changeCurrentMonth(currentMonth.minusMonths(1)));

    Button nextBtn = new Button(">");
    nextBtn.setOnAction(evt -> changeCurrentMonth(currentMonth.plusMonths(1)));

    currentMonthLabel = new Label(currentMonth.format(CURRENT_MONTH_LABEL_FORMATTER));

    HBox controlBox = new HBox(prevBtn, currentMonthLabel, nextBtn);
    controlBox.setSpacing(10.);
    controlBox.setAlignment(Pos.CENTER_LEFT);

    ScrollPane scp = new ScrollPane(grid);
    scp.setStyle("-fx-background-color: transparent");
    scp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    pane = new VBox(controlBox, scp);
    pane.setFillWidth(false);
    pane.setSpacing(10);
  }

  @Override
  public Node getView() {
    return pane;
  }

  protected void setMonthChangeEvent(MonthChangeEvent monthChangeEvent) {
    this.monthChangeEvent = monthChangeEvent;
  }

  private void changeCurrentMonth(LocalDate currentMonth) {

    if (this.monthChangeEvent != null) {
      monthChangeEvent.monthChanged(currentMonth);
    }
  }

  private void buildCalendar(LocalDate monthView, Period<LocalDate> period) {
    clearCalendar();

    TemporalField woyField = WeekFields.of(Locale.getDefault()).weekOfYear();

    final int firstWeek = period.getStart().get(woyField);
    final int lastWeek = period.getEnd().get(woyField);

    int rows = (firstWeek > lastWeek) ? (firstWeek) : (lastWeek - firstWeek + 1);

    // Set rows constraints
    grid.getRowConstraints().clear();
    grid.getRowConstraints().add(firstRowConstraints);
    for (int i = 0; i < rows; i++) {
      RowConstraints row = new RowConstraints();
      row.setMinHeight(115);
      grid.getRowConstraints().add(row);
    }

    LocalDate currentDay = period.getStart();

    for (int i = 1; i <= rows; i++) {
      for (int j = 0; j < COLUMNS; j++) {

        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);

        final MonthReportCell cell;

        if (j == WEEK_COLUMN) {
          cell = loadView(WeekSummaryCell.class);
          weekSummaryCells.put(currentDay.get(woyField), (WeekSummaryCell) cell);
        }
        else {
          cell = loadView(AttendanceCell.class);
          ((AttendanceCell) cell).setDay(currentDay);

          attendanceCells.put(currentDay, (AttendanceCell) cell);
        }
        pane.getChildren().add(cell.getView());


        pane.getStyleClass().add("monthreport-grid-cell");
        if (j == 0) {
          pane.getStyleClass().add("first-column");
        }
        if (j == WEEK_COLUMN) {
          pane.getStyleClass().addAll("week-column", "last-column");
        }
        if (i == rows) {
          pane.getStyleClass().add("last-row");
        }

        if (currentDay.getMonth() != monthView.getMonth()) {
          if (j != WEEK_COLUMN) {
            pane.getStyleClass().add("othermonth-cell");
          }

          cell.setFaded(true);
        }

        grid.add(pane, j, i);

        currentDay = currentDay.plusDays(1);
      }
    }
  }

  private void clearCalendar() {
    ObservableList<Node> children = grid.getChildren();

    List<Node> nodesToRemove = new ArrayList<>();

    for (Node node : children) {
      if (GridPane.getRowIndex(node) > 0) {
        nodesToRemove.add(node);
      }
    }

    grid.getChildren().removeAll(nodesToRemove);

    attendanceCells.clear();
    weekSummaryCells.clear();
  }

  @Override
  public void setData(MonthReport data) {
    this.currentMonth = data.getDate();

    currentMonthLabel.setText(CURRENT_MONTH_LABEL_FORMATTER.format(currentMonth));
    buildCalendar(currentMonth, data.getPeriod());

    // Set attendances
    for (Attendance attendance : data.getAttendances()) {
      AttendanceCell cell = attendanceCells.get(attendance.getDate());
      if (cell != null) {
        cell.setData(attendance);
      }
    }

    // Set summaries
    for (Integer week : data.getWeekSummaries().keySet()) {

      WeekSummaryCell cell = weekSummaryCells.get(week);
      if (cell != null) {
        cell.setData(data.getWeekSummaries().get(week));
      }

    }
  }

  @Override
  public MonthReport getData() {
    return null;
  }
}

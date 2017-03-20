package ysaak.hera.nexus.gui.common;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Helper to create grid form
 */
public class GridFormHelper {
  private static final int DEFAULT_COL_SPACING = 10;

  private final int nbColumns;
  private final double colSpacing;

  private final GridPane pane;

  private int currentColumn = 0;
  private int currentLine = 0;

  public GridFormHelper(int nbColumns) {
    this.nbColumns = nbColumns;
    this.colSpacing = DEFAULT_COL_SPACING / 2.;

    pane = new GridPane();

    ColumnConstraints colConst = new ColumnConstraints();
    colConst.setMinWidth(200.);
    colConst.setFillWidth(true);
    colConst.setPercentWidth(50.);
    colConst.setHalignment(HPos.LEFT);

    pane.getColumnConstraints().setAll(colConst, colConst);
  }

  public void buildColumnsSpacing(Node node) {
    GridPane.setMargin(node, new Insets(colSpacing));
  }

  public void addComponent(String text, Node comp) {
    addComponent(text, comp, false);
  }

  public void addComponent(String text, Node comp, boolean required) {

    if (currentColumn == nbColumns) {
      nextLine();
    }

    // Init label
    final Label label = new Label(text);
    label.getStyleClass().add("form-label");
    label.setOnMouseClicked(event -> comp.requestFocus());


    HBox labelPane =  new HBox(5);
    labelPane.getChildren().add(label);

    if (required) {
      Label reqLabel = new Label("*");
      reqLabel.getStyleClass().add("form-label-required");
      labelPane.getChildren().add(reqLabel);
    }

    buildColumnsSpacing(labelPane);
    buildColumnsSpacing(comp);

    this.pane.add(labelPane, currentColumn, currentLine);
    this.pane.add(comp, currentColumn, currentLine + 1);

    currentColumn++;
  }

  public void nextLine() {
    currentLine += 2;
    currentColumn = 0;
  }

  public GridPane getPane() {
    return pane;
  }
}

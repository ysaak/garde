package ysaak.hera.nexus.gui.common.buttonbar;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

/**
 * Template for a custom button bar
 */
public class CustomButtonBar extends ButtonBar {
  private final HBox leftActions;

  private final HBox rightActions;

  public CustomButtonBar() {
    super();

    leftActions = new HBox(5.);
    rightActions = new HBox(5.);


    setLeft(leftActions);
    setRight(rightActions);
  }

  public void setLeftNodes(Node...nodes) {
    leftActions.getChildren().setAll(nodes);
  }

  public void setRightNodes(Node...nodes) {
    rightActions.getChildren().setAll(nodes);
  }
}

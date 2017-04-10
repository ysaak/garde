package ysaak.garde.gui.common.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Material design card
 */
public class Card {

  private final BorderPane pane;
  private final Label title;

  public Card(String title, Node center) {
    pane = new BorderPane();
    pane.getStyleClass().add("card");

    this.title = new Label(title);
    this.title.getStyleClass().add("card-title");

    setCenter(center);
    computeTitleVisibility();
  }

  private void computeTitleVisibility() {
    if (title.getText() != null && title.getText().trim().length() > 0) {
      if (pane.getTop() == null) {
        pane.setTop(title);
      }
    }
    else {
      pane.setTop(null);
    }
  }

  public void setTitle(String title) {
    this.title.setText(title);
    computeTitleVisibility();
  }

  public void setCenter(Node value) {
    pane.setCenter(value);
  }

  public Pane getView() {
    return pane;
  }
}

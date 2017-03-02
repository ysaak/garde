package ysaak.hera.nexus.gui.common.buttonbar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

/**
 * Basic button action
 */
public abstract class Action implements EventHandler<ActionEvent> {


  private String text = null;
  private Node graphic = null;

  private BooleanProperty disabledProperty = new SimpleBooleanProperty(false);

  public Action(String text) {
    this(text, null);
  }

  public Action(String text, Node graphic) {
    this.text = text;
    this.graphic = graphic;
  }

  public String getText() {
    return text;
  }

  public void setGraphic(Node graphic) {
    this.graphic = graphic;
  }

  public Node getGraphic() {
    return graphic;
  }

  public void setDisabled(boolean disabled) {
    this.disabledProperty.set(disabled);
  }

  public ReadOnlyBooleanProperty disabledProperty() {
    return disabledProperty;
  }
}

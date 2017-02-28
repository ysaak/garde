package ysaak.hera.nexus.gui.common.components;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import jfxtras.scene.layout.VBox;

public class RadioButtonGroup<T> {
  
  private final Pane pane;
  
  private final ToggleGroup group;
  
  public RadioButtonGroup() {
    this(Orientation.HORIZONTAL);
  }
  
  public RadioButtonGroup(Orientation orientation) {
    if (orientation == Orientation.HORIZONTAL) {
      pane = new HBox(10.);
    }
    else {
      pane = new VBox(10.);
    }
    
    group = new ToggleGroup();
  }
  
  @SuppressWarnings("unchecked")
  public void setItems(T ...items) {
    
    boolean selected = false;
    
    for (T item : items) {
      
      RadioButton btn = new RadioButton(item.toString());
      btn.setUserData(item);
      group.getToggles().add(btn);

      pane.getChildren().add(btn);

      if (!selected) {
        btn.setSelected(true);
        selected = true;
      }
    }
  }
  
  @SuppressWarnings("unchecked")
  public T getSelectedItem() {
    return (T) group.getSelectedToggle().getUserData();
  }
  
  public void setSelectedItem(T item) {
    
    for (Toggle t : group.getToggles()) {
      if (item.equals(t.getUserData())) {
        t.setSelected(true);
        break;
      }
    }
  }
  
  public Node getView() {
    return pane;
  }
}

package ysaak.garde.gui.common.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import jfxtras.scene.layout.VBox;

public class RadioButtonGroup<T> {
  
  private final Pane pane;
  
  private final ToggleGroup group;
  private Callback<T, String> textFactory = param -> (param != null) ? param.toString() : null;

  private ObjectProperty<T> selectedItemProperty;

  public RadioButtonGroup() {
    this(Orientation.HORIZONTAL);
  }

  @SuppressWarnings("unchecked")
  public RadioButtonGroup(Orientation orientation) {
    if (orientation == Orientation.HORIZONTAL) {
      pane = new HBox(10.);
    }
    else {
      pane = new VBox(10.);
    }
    
    group = new ToggleGroup();
    selectedItemProperty = new SimpleObjectProperty<T>(null);

    group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> selectedItemProperty.set((T) newValue.getUserData()));
  }
  
  @SuppressWarnings("unchecked")
  public void setItems(T ...items) {
    
    boolean selected = false;
    
    for (T item : items) {
      
      RadioButton btn = new RadioButton(textFactory.call(item));
      btn.setUserData(item);
      group.getToggles().add(btn);

      pane.getChildren().add(btn);

      if (!selected) {
        btn.setSelected(true);
        selected = true;
      }
    }
  }

  public void setTextFactory(Callback<T, String> factory) {
    if (factory == null) {
      throw new NullPointerException("Factory is null");
    }

    this.textFactory = factory;
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

  public ReadOnlyObjectProperty<T> selectedItemProperty() {
    return selectedItemProperty;
  }
  
  public Node getView() {
    return pane;
  }
}

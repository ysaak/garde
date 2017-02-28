package ysaak.hera.nexus.gui.common.components.grid;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public abstract class ToggleableView<T> implements Toggle {
  private final ObjectProperty<ToggleGroup> toggleGroup = new SimpleObjectProperty<ToggleGroup>(null);
  private final BooleanProperty selectedProperty = new SimpleBooleanProperty(false);
  
  protected T data;
  
  public abstract void setData(T data);
  
  public abstract T getData();
  
  public abstract Node getView();
  
  @Override
  public ToggleGroup getToggleGroup() {
    return toggleGroup.get();
  }

  @Override
  public void setToggleGroup(ToggleGroup toggleGroup) {
    this.toggleGroup.set(toggleGroup);
  }

  @Override
  public ObjectProperty<ToggleGroup> toggleGroupProperty() {
    return toggleGroup;
  }

  @Override
  public boolean isSelected() {
    return selectedProperty.get();
  }

  @Override
  public void setSelected(boolean selected) {
    selectedProperty.set(selected);
  }

  @Override
  public BooleanProperty selectedProperty() {
    return selectedProperty;
  }

  @Override
  public Object getUserData() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setUserData(Object value) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public ObservableMap<Object, Object> getProperties() {
    // TODO Auto-generated method stub
    return null;
  }
}

package ysaak.garde.gui.common.buttonbar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public abstract class ButtonBar extends BorderPane {
  
  private ButtonBarListener listener;
  
  private final BooleanProperty hasChanged = new SimpleBooleanProperty();
  private final BooleanProperty isValid = new SimpleBooleanProperty(true);

  public ButtonBar() {
    super();
    setPadding(new Insets(5, 5, 5, 5));
    
    isValid.addListener((observable, oldValue, newValue) -> handleValidityForm(hasChanged.get(), newValue));
    hasChanged.addListener((observable, oldValue, newValue) -> handleValidityForm(newValue, isValid.get()));
  }
  
  public BooleanProperty isValidProperty() {
    return isValid;
  }
  
  public BooleanProperty hasChangedProperty() {
    return hasChanged;
  }
  
  protected void handleValidityForm(boolean hasChanged, boolean isValid) {
    
  }
  
  public void setListener(ButtonBarListener listener) {
    this.listener = listener;
  }
  
  protected void fireEvent(ButtonBarAction action) {
    if (listener != null) {
      listener.actionPerformed(action);
    }
  }
}

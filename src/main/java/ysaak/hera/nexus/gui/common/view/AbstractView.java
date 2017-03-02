package ysaak.hera.nexus.gui.common.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ysaak.hera.nexus.gui.common.Context;

public abstract class AbstractView<DATA> implements View<DATA> {
  private ViewListener listener;
  
  private final BooleanProperty hasChanged = new SimpleBooleanProperty();

  private final BooleanProperty isValid = new SimpleBooleanProperty(true);

  protected DATA originalData = null;
  
  @Override
  public void setListener(ViewListener listener) {
    this.listener = listener;
  }
  
  @Override
  public ReadOnlyBooleanProperty isValidProperty() {
    return isValid;
  }
  
  @Override
  public ReadOnlyBooleanProperty hasChangedProperty() {
    return hasChanged;
  }
  
  protected void fireOpenFormRequest(String viewCode, Context context) {
    if (listener != null) {
      listener.openForm(viewCode, context);
    }
  }

  protected <T> T loadView(Class<T> viewClazz) {
    return (listener != null) ? listener.getViewLoader().loadView(viewClazz) : null;
  }
}

package ysaak.hera.nexus.gui.common.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.service.translation.TranslationFacade;

public abstract class AbstractView<DATA> implements View<DATA>, TranslationAware {
  private ViewListener listener;
  
  private final BooleanProperty hasChanged = new SimpleBooleanProperty();

  private final BooleanProperty isValid = new SimpleBooleanProperty(true);

  protected DATA originalData = null;

  protected TranslationFacade translationFacade;
  
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

  @Override
  public void setTranslationFacade(TranslationFacade translationFacade) {
    this.translationFacade = translationFacade;
  }

  protected <T> T loadView(Class<T> viewClazz) {
    return (listener != null) ? listener.getViewLoader().loadView(viewClazz) : null;
  }
}

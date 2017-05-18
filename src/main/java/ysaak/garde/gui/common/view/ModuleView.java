package ysaak.garde.gui.common.view;

import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * Created by ysaak on 18/05/2017.
 */
public interface ModuleView<DATA> extends View<DATA> {

  ReadOnlyBooleanProperty isValidProperty();

  ReadOnlyBooleanProperty hasChangedProperty();

  void setListener(ViewListener listener);
}

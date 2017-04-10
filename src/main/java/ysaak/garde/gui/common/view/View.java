package ysaak.garde.gui.common.view;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;

public interface View<DATA> {

  void initialize();

  Node getView();
  
  void setData(DATA data);
  
  DATA getData();
  
  ReadOnlyBooleanProperty isValidProperty();
  
  ReadOnlyBooleanProperty hasChangedProperty();
  
  void setListener(ViewListener listener);
}

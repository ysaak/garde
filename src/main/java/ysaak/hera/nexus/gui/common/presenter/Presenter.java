package ysaak.hera.nexus.gui.common.presenter;

import javafx.scene.Node;
import ysaak.hera.nexus.gui.common.Context;

public interface Presenter {
  void init();
  
  Node getView();
  
  void startLoadData(final Context context);
}

package ysaak.hera.nexus.gui.common.presenter;

import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.components.ModulePane;

public interface Presenter {
  void init();
  
  ModulePane getView();
  
  void startLoadData(final Context context);
}

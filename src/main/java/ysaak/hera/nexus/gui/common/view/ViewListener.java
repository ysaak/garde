package ysaak.hera.nexus.gui.common.view;

import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.ViewLoader;

public interface ViewListener {
  void openForm(String viewCode, Context context);

  ViewLoader getViewLoader();
}

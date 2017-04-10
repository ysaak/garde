package ysaak.garde.gui.common.view;

import ysaak.garde.gui.common.ViewLoader;
import ysaak.garde.gui.common.Context;

public interface ViewListener {
  void openForm(String viewCode, Context context);

  ViewLoader getViewLoader();
}

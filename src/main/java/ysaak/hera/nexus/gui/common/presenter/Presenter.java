package ysaak.hera.nexus.gui.common.presenter;

import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.components.ModulePane;

public interface Presenter {
  /**
   * Initialize the presenter
   */
  void init();

  /**
   * Returns the root node of the presenter
   * @return Root node
   */
  ModulePane getView();

  /**
   * Start data loading
   * @param context Data context
   */
  void startLoadData(Context context);

  /**
   * Reload data using the previous context
   */
  void startReloadData();

  /**
   * Indicates that the data must be reloaded
   * @return True if data must be reloaded - false otherwise
   */
  boolean reloadOnDisplay();
}

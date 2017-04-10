package ysaak.garde.gui.common.actions;

/**
 * Listener for basic actions
 */
public interface ActionListener<T> {
  void onAction(ActionType action, T data);
}

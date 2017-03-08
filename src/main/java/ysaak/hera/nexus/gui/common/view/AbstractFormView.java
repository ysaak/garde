package ysaak.hera.nexus.gui.common.view;

import javafx.scene.Node;
import ysaak.hera.nexus.gui.common.actions.ActionListener;
import ysaak.hera.nexus.gui.common.actions.ActionType;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;

import java.util.Collections;
import java.util.List;

public abstract class AbstractFormView<DATA> extends AbstractView<DATA> {

  private ActionListener<DATA> actionListener = null;

  /**
   * Title of the module
   * @return Title
   */
  public abstract String getTitle();

  /**
   * Components of the right toolbar
   * @return Components for the right toolbar
   */
  public List<Node> getToolbarComponents() {
    return Collections.emptyList();
  }

  /**
   * Button bar of the module
   * @return Button bar of the module
   */
  public ButtonBar getButtonBar() {
    return null;
  }

  /**
   * Sets the action listener for this view
   * @param actionListener Action listener
   */
  public void setActionListener(ActionListener<DATA> actionListener) {
    this.actionListener = actionListener;
  }

  /**
   * Fire an action
   * @param action Action fired
   * @param data Associated data
   */
  protected void fireActionEvent(ActionType action, DATA data) {
    if (this.actionListener != null) {
      this.actionListener.onAction(action, data);
    }
  }
}

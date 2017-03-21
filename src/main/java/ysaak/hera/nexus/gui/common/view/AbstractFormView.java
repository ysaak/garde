package ysaak.hera.nexus.gui.common.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import ysaak.hera.nexus.gui.common.actions.ActionListener;
import ysaak.hera.nexus.gui.common.actions.ActionType;

import java.util.Collections;
import java.util.List;

public abstract class AbstractFormView<DATA> extends AbstractView<DATA> {

  private ActionListener<DATA> actionListener = null;

  private StringProperty title = new SimpleStringProperty();

  @Deprecated
  public AbstractFormView() {
    title.set(getTitle());
  }

  public AbstractFormView(String title) {
    this.title.set(title);
  }

  /**
   * Defines the new title of the form
   * @param title New title
   */
  protected void setTitle(String title) {
    this.title.set(title);
  }

  /**
   * Returns the title property
   * @return Title
   */
  public StringProperty titleProperty() {
    return title;
  }

  /**
   * Title of the module
   * @return Title
   */
  @Deprecated
  public String getTitle() {
    return null;
  }

  /**
   * Components of the right toolbar
   * @return Components for the right toolbar
   */
  public List<Node> getToolbarComponents() {
    return Collections.emptyList();
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

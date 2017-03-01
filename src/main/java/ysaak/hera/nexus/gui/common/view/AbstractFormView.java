package ysaak.hera.nexus.gui.common.view;

import javafx.scene.Node;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;

import java.util.Collections;
import java.util.List;

public abstract class AbstractFormView<DATA> extends AbstractView<DATA> {

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
}

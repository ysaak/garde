package ysaak.hera.nexus.gui.common.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class AbstractFxmlView<DATA> extends AbstractView<DATA> {

  @FXML
  protected Pane rootPane;

  @Override
  public Node getView() {
    return rootPane;
  }
}

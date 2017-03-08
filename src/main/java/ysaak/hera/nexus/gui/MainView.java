package ysaak.hera.nexus.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.springframework.stereotype.Component;
import ysaak.hera.nexus.gui.common.components.ModulePane;
import ysaak.hera.nexus.gui.common.view.AbstractView;

@Component
public class MainView extends AbstractView<Void> {

  @FXML
  private BorderPane rootPane;

  @FXML
  private AnchorPane centerPane;

  private Node leftPane;

  private ModulePane centerNode = null;

  @Override
  public void initialize() {
  }

  public void setCenterNode(ModulePane node) {
    centerPane.getChildren().clear();

    if (node != null) {
      centerNode = node;

      // Set anchors
      AnchorPane.setTopAnchor(node.getView(), 0.);
      AnchorPane.setBottomAnchor(node.getView(), 0.);
      AnchorPane.setLeftAnchor(node.getView(), 0.);
      AnchorPane.setRightAnchor(node.getView(), 0.);

      // Compute padding
      computeCenterNodePadding();

      centerPane.getChildren().add(node.getView());
    }
  }

  public void setLeftPane(Node node) {
    this.leftPane = node;

    leftPane.visibleProperty().addListener((observable, oldValue, newValue) -> {

      if (newValue != null && newValue) {
        rootPane.setLeft(leftPane);
      }
      else {
        rootPane.setLeft(null);
      }
    });

    if (node.isVisible()) {
      rootPane.setLeft(node);
    }

    computeCenterNodePadding();
  }

  /**
   * Compute the center node padding
   */
  private void computeCenterNodePadding() {
    if (centerNode != null) {
      double leftPadding = rootPane.getLeft() != null ? 40. : 20.;
      centerNode.setPadding(new Insets(20., 20., 20., leftPadding));
    }
  }

  @Override
  public void setData(Void data) {/**/}

  @Override
  public Void getData() { return null; }

  @Override
  public Node getView() {
    return rootPane;
  }
}

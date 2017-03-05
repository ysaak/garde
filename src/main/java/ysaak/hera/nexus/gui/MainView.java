package ysaak.hera.nexus.gui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;
import ysaak.hera.nexus.gui.common.view.AbstractView;

@Component
public class MainView extends AbstractView<Void> {
  @FXML
  private StackPane rootPane;

  @FXML
  private BorderPane basePane;

  @FXML
  private AnchorPane centerPane;

  private Node leftPane;

  @Override
  public void initialize() {
    // Unused
  }
  
  public void setCenterNode(Node node) {
    centerPane.getChildren().clear();
    centerPane.getChildren().add(node);
  }

  public void setLeftPane(Node node) {
    this.leftPane = node;

    BorderPane.setMargin(leftPane, new Insets(0, 10., 0, 0));

    leftPane.visibleProperty().addListener((observable, oldValue, newValue) -> {

      if (newValue != null && newValue) {
        basePane.setLeft(leftPane);
      }
      else {
        basePane.setLeft(null);
      }
    });

    if (node.isVisible()) {
      basePane.setLeft(node);
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

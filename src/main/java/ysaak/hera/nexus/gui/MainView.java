package ysaak.hera.nexus.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;
import ysaak.hera.nexus.gui.common.view.AbstractView;

@Component
public class MainView extends AbstractView<Void> {
  @FXML
  protected StackPane rootPane;

  @FXML
  private AnchorPane centerPane;

  @FXML
  private Pane navbarButtonsPane;

  @Override
  public void initialize() {

  }

  public void addNavbarElement(Node node) {
    navbarButtonsPane.getChildren().add(node);
  }
  
  public void setCenterNode(Node node) {
    centerPane.getChildren().clear();
    centerPane.getChildren().add(node);
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

package ysaak.hera.nexus.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;
import ysaak.hera.nexus.gui.common.view.AbstractFxmlView;

@Component
public class MainView extends AbstractFxmlView<Void> {

  @FXML
  private AnchorPane centerPane;

  @FXML
  private Pane navbarButtonsPane;

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
}

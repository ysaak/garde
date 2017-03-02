package ysaak.hera.nexus.gui.common.buttonbar;

import com.google.common.collect.Lists;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * Template for a custom button bar
 */
public class CustomButtonBar extends ButtonBar {
  private final HBox leftActions;

  private final HBox rightActions;

  private final List<String> customStyles = Lists.newArrayList();

  public CustomButtonBar() {
    super();

    leftActions = new HBox(5.);
    rightActions = new HBox(5.);


    setLeft(leftActions);
    setRight(rightActions);
  }

  public void setCustomStyles(List<String> customStyles) {
    this.customStyles.clear();
    this.customStyles.addAll(customStyles);
  }

  public void setLeftActions(Action...actions) {
    addNodes(leftActions.getChildren(), actions);
  }

  public void setRightActions(Action...actions) {
    addNodes(rightActions.getChildren(), actions);
  }

  private void addNodes(ObservableList<Node> children, Action[] actions) {
    for (Action action : actions) {

      Button btn = new Button(action.getText(), action.getGraphic());

      btn.setOnAction(action);

      btn.disableProperty().bind(action.disabledProperty());

      if (customStyles.isEmpty()) {
        btn.getStyleClass().add("hbtn");
      }
      else {
        btn.getStyleClass().addAll(customStyles);
      }

      children.add(btn);
    }
  }
}

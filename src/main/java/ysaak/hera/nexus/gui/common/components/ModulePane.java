package ysaak.hera.nexus.gui.common.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;

import java.util.List;

/**
 * Main module pane
 */
public class ModulePane {
  private static final double SPACING = 20.;
  private static final double MARGIN = 10.;

  private final BorderPane mainPane;

  private final BorderPane topPane;

  private final Label titleLabel;

  private final HBox toolbarPane;


  public ModulePane() {
    titleLabel = new Label(" ");
    titleLabel.getStyleClass().add("module-title");

    toolbarPane = new HBox(20.);

    topPane = new BorderPane();
    topPane.setLeft(titleLabel);
    topPane.setRight(toolbarPane);

    BorderPane.setMargin(topPane, new Insets(0,0,SPACING,0));

    mainPane = new BorderPane();
    mainPane.setTop(topPane);

    BorderPane.setMargin(mainPane, new Insets(MARGIN));
  }

  public void setTopBarVisible(boolean visible) {
    topPane.setVisible(visible);
  }

  public void setTitle(String title) {
    titleLabel.setText(title);
  }

  public void setToolbarComponents(List<Node> components) {
    toolbarPane.getChildren().setAll(components);
  }

  public void setCenter(Node center) {
    mainPane.setCenter(center);
  }

  public void setButtonBar(ButtonBar buttonBar) {
    if (buttonBar != null) {
      ButtonBar.setMargin(buttonBar, new Insets(SPACING, 0, 0, 0));
    }

    mainPane.setBottom(buttonBar);
  }

  public Pane getView() {
    return mainPane;
  }

}

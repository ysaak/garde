package ysaak.hera.nexus.gui.common.components;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.service.task.TaskMonitoringInterface;
import ysaak.hera.nexus.service.task.TaskType;

import java.util.List;

/**
 * Main module pane
 */
public class ModulePane implements TaskMonitoringInterface {
  private static final double SPACING = 20.;
  private static final double MARGIN = 10.;

  private final StackPane mainPane;

  private final BorderPane centerPane;

  private final BorderPane topPane;

  private final Label titleLabel;

  private final HBox toolbarPane;

  private final OverlayIndicator overlayIndicator;

  public ModulePane() {
    titleLabel = new Label(" ");
    titleLabel.getStyleClass().add("module-title");
    titleLabel.textProperty().addListener((object, oldValue, newValue) -> computeTopPaneVisibility());

    toolbarPane = new HBox(20.);


    topPane = new BorderPane();
    topPane.setLeft(titleLabel);
    topPane.setRight(toolbarPane);

    BorderPane.setMargin(topPane, new Insets(0,0,SPACING,0));

    centerPane = new BorderPane();
    centerPane.setTop(topPane);

    BorderPane.setMargin(centerPane, new Insets(MARGIN));

    // Overlay indicator
    overlayIndicator = new OverlayIndicator();

    mainPane = new StackPane(centerPane, overlayIndicator.getView());
  }

  public StringProperty titleProperty() {
    return titleLabel.textProperty();
  }

  public void setToolbarComponents(List<Node> components) {
    toolbarPane.getChildren().setAll(components);
    computeTopPaneVisibility();
  }

  private void computeTopPaneVisibility() {
    if (titleLabel.getText().trim().length() > 0 || toolbarPane.getChildren().size() > 0) {
      // If the pane has a title or a node in the toolbar, the top pane is visible
      if (centerPane.getTop() != null) {
        centerPane.setTop(topPane);
      }
    }
    else {
      centerPane.setTop(null);
    }
  }

  public void setCenter(Node center) {
    centerPane.setCenter(center);
  }

  public void setButtonBar(ButtonBar buttonBar) {
    if (buttonBar != null) {
      ButtonBar.setMargin(buttonBar, new Insets(SPACING, 0, 0, 0));
    }

    centerPane.setBottom(buttonBar);
  }

  public Pane getView() {
    return mainPane;
  }

  public void setPadding(Insets insets) {
    centerPane.setPadding(insets);
  }

  @Override
  public void setTaskType(TaskType type) {
    overlayIndicator.setType(type);
  }

  @Override
  public void setLongTaskStarted() {
    overlayIndicator.setVisible(true);
  }

  @Override
  public void setLongTaskEnded() {
    overlayIndicator.setVisible(false);
  }
}

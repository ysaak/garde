package ysaak.garde.gui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import ysaak.garde.gui.common.components.ModulePane;
import ysaak.garde.gui.common.components.OverlayIndicator;
import ysaak.garde.gui.leftpanel.LeftPanelView;


public class MainView {
  private final static Duration SLIDE_DURATION = Duration.millis(200);
  private final static Duration MODULE_FADE_DURATION = Duration.millis(300);

  private final StackPane rootPane;
  private final AnchorPane mainPane;

  private LeftPanelView leftPane = null;
  private Node modulePane = null;

  private final OverlayIndicator overlayIndicator;

  public MainView() {
    mainPane = new AnchorPane();
    mainPane.setMinSize(700, 400);
    mainPane.setPrefSize(1000, 600);

    overlayIndicator = new OverlayIndicator();

    rootPane = new StackPane(mainPane, overlayIndicator.getView());
  }

  /**
   * Set the current module to display
   * @param node Module pane to display
   */
  public void setModulePane(final ModulePane node) {
    if (node != null) {
      if (modulePane != null) {
        FadeTransition transition = new FadeTransition(MODULE_FADE_DURATION, modulePane);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.setOnFinished(event -> displayModuleNode(node));
        transition.play();
      }
      else {
        displayModuleNode(node);
      }
    }
  }

  /**
   * Display a module in the root pane
   * @param module Module to display
   */
  private void displayModuleNode(final ModulePane module) {
    // Set anchors
    this.modulePane = module.getView();

    // Compute and set anchors
    computeModuleNodeAnchors();

    module.getView().setOpacity(0);
    mainPane.getChildren().add(module.getView());

    FadeTransition transition = new FadeTransition(MODULE_FADE_DURATION, this.modulePane);
    transition.setFromValue(0);
    transition.setToValue(1);
    transition.play();
  }

  /**
   * Defines the left pane
   * @param node Left pane
   */
  public void setLeftPane(LeftPanelView node) {
    this.leftPane = node;

    AnchorPane.setTopAnchor(leftPane.getView(), 0.);
    AnchorPane.setBottomAnchor(leftPane.getView(), 0.);
    AnchorPane.setLeftAnchor(leftPane.getView(), 0.);

    leftPane.visibleProperty().addListener((obs, ov, nv) -> setLeftPaneVisible(nv));
    setLeftPaneVisible(leftPane.isVisible());
    leftPane.getView().translateXProperty().addListener((obs, ov, nv) -> computeModuleNodeAnchors());
  }

  /**
   * Changes the visibility of the left pane
   * @param visible TRUE to make show - FALSE to hide
   */
  private void setLeftPaneVisible(boolean visible) {
    if (visible) {
      if (!mainPane.getChildren().contains(leftPane.getView())) {
        mainPane.getChildren().add(leftPane.getView());

        leftPane.getView().setTranslateX(-leftPane.getWidth());

        // Animation
        TranslateTransition openNav = new TranslateTransition(SLIDE_DURATION, leftPane.getView());
        openNav.setToX(0);
        openNav.play();
      }
    }
    else {
      if (mainPane.getChildren().contains(leftPane.getView())) {
        // Animation
        TranslateTransition openNav = new TranslateTransition(SLIDE_DURATION, leftPane.getView());
        openNav.setToX(-leftPane.getWidth());
        openNav.setOnFinished(event -> mainPane.getChildren().remove(leftPane.getView()));
        openNav.play();
      }

      leftPane.getView().setTranslateX(-leftPane.getWidth());
    }

    computeModuleNodeAnchors();
  }

  /**
   * Compute the module node anchors according to left pane position
   */
  private void computeModuleNodeAnchors() {

    if (modulePane != null) {

      if (leftPane != null) {
        AnchorPane.setLeftAnchor(modulePane, leftPane.getWidth() + leftPane.getView().getTranslateX());
      }
      else {
        AnchorPane.setLeftAnchor(modulePane, 0.);
      }

      AnchorPane.setTopAnchor(modulePane, 0.);
      AnchorPane.setBottomAnchor(modulePane, 0.);
      AnchorPane.setRightAnchor(modulePane, 0.);
    }
  }

  /**
   * Returns the root pane
   * @return Root pane
   */
  public Node getView() {
    return rootPane;
  }

  public OverlayIndicator getOverlayIndicator() {
    return overlayIndicator;
  }
}

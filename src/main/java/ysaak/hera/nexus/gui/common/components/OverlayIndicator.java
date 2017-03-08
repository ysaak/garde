package ysaak.hera.nexus.gui.common.components;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ysaak.hera.nexus.service.task.TaskType;
import ysaak.hera.nexus.service.translation.I18n;

/**
 * Progress indicator as overlay
 */
public class OverlayIndicator {

  private final VBox overlay;

  private final Label textLabel;

  private boolean visible = false;

  public OverlayIndicator() {
    overlay = new VBox(10.);
    overlay.setAlignment(Pos.CENTER);
    overlay.getStyleClass().add("load-indicator");
    //overlay.visibleProperty().bind(loadIndicatorVisible);


    textLabel = new Label(I18n.get("common.loading"));
    textLabel.getStyleClass().add("text");

    ProgressIndicator indicator = new ProgressIndicator();
    indicator.setMaxSize(150, 150);

    overlay.getChildren().addAll(textLabel, indicator);

    overlay.setOpacity(0.);
    overlay.setVisible(false);
  }

  public VBox getView() {
    return overlay;
  }

  public void setType(TaskType type) {
    if (type == TaskType.LOAD) {
      textLabel.setText(I18n.get("common.loading"));
    }
    else {
      textLabel.setText(I18n.get("common.updating"));
    }
  }

  public void setVisible(boolean visible) {
    if (this.visible != visible) {
      if (visible) {
        show();
      }
      else {
        hide();
      }

      this.visible = visible;
    }
  }

  private void show() {
    FadeTransition ft = new FadeTransition(Duration.millis(300), overlay);
    ft.setFromValue(0);
    ft.setToValue(1);
    overlay.setOpacity(0);
    overlay.setVisible(true);
    ft.play();
  }
  private void hide() {
    FadeTransition ft = new FadeTransition(Duration.millis(300), overlay);
    ft.setFromValue(1);
    ft.setToValue(0);
    ft.play();
    ft.setOnFinished(evt -> overlay.setVisible(false));
  }
}

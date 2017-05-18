package ysaak.garde.gui.common.components;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ysaak.garde.service.task.TaskMonitoringInterface;
import ysaak.garde.service.task.TaskType;
import ysaak.garde.service.translation.I18n;

/**
 * Progress indicator as overlay
 */
public class OverlayIndicator implements TaskMonitoringInterface {

  private final VBox overlay;

  private final Label textLabel;

  private boolean visible = false;

  public OverlayIndicator() {
    overlay = new VBox(10.);
    overlay.setAlignment(Pos.CENTER);
    overlay.getStyleClass().add("load-indicator");

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

  @Override
  public void setTaskType(TaskType type) {
    if (type == TaskType.LOAD) {
      textLabel.setText(I18n.get("common.loading"));
    }
    else {
      textLabel.setText(I18n.get("common.updating"));
    }
  }

  @Override
  public void setLongTaskStarted() {
    setVisible(true);
  }

  @Override
  public void setLongTaskEnded() {
    setVisible(false);
  }

  private void setVisible(boolean visible) {
    if (this.visible != visible) {
      if (visible) {
        // Display the overlay
        FadeTransition ft = new FadeTransition(Duration.millis(300), overlay);
        ft.setFromValue(0);
        ft.setToValue(1);
        overlay.setOpacity(0);
        overlay.setVisible(true);
        ft.play();
      }
      else {
        // Hide the overlay
        FadeTransition ft = new FadeTransition(Duration.millis(300), overlay);
        ft.setFromValue(1);
        ft.setToValue(0);
        ft.setOnFinished(evt -> overlay.setVisible(false));
        ft.play();
      }

      this.visible = visible;
    }
  }
}

package ysaak.garde.gui.leftpanel;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.service.translation.I18n;

/**
 * Left panel displaying child information
 */
public class LeftPanelView {
  private static final double PANEL_PADDING = 10.;
  private static final double PANEL_WIDTH = 200. + 2 * PANEL_PADDING;

  private final BorderPane rootPane;
  private final Label firstNameLabel;
  private final Label lastNameLabel;

  private JFXButton terminateButton;

  private final BooleanProperty visible = new SimpleBooleanProperty(true);

  public LeftPanelView() {
    VBox topPane = new VBox();

    ImageView childImage = new ImageView(getClass().getResource("/testimg.jpg").toExternalForm());
    lastNameLabel = new Label(" ");
    lastNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em");
    firstNameLabel = new Label(" ");

    topPane.getChildren().addAll(childImage, lastNameLabel, firstNameLabel);

    // Bottom pane
    terminateButton = new JFXButton(I18n.get("common.button.terminate"));
    terminateButton.setMaxWidth(Double.MAX_VALUE);

    VBox bottomPane = new VBox(terminateButton);
    bottomPane.setFillWidth(true);
    bottomPane.setAlignment(Pos.CENTER);

    rootPane = new BorderPane();
    rootPane.setTop(topPane);
    rootPane.setBottom(bottomPane);

    rootPane.getStyleClass().add("left-panel");

    rootPane.setPadding(new Insets(PANEL_PADDING));
    rootPane.setPrefWidth(PANEL_WIDTH);
    rootPane.setMinWidth(PANEL_WIDTH);
    rootPane.setMaxWidth(PANEL_WIDTH);
  }

  public void setOnTerminateAction(EventHandler<ActionEvent> value) {
    terminateButton.setOnAction(value);
  }

  public void setChildData(ChildDTO child) {
    if (child != null) {
      lastNameLabel.setText(child.getLastName());
      firstNameLabel.setText(child.getFirstName());
    }
    else {
      lastNameLabel.setText(" ");
      firstNameLabel.setText(" ");
    }
  }

  public BooleanProperty visibleProperty() {
    return visible;
  }

  public void setVisible(boolean visible) {
    this.visible.set(visible);
  }

  public boolean isVisible() {
    return visible.get();
  }

  public double getWidth() {
    return PANEL_WIDTH;
  }

  public Region getView() {
    return rootPane;
  }
}

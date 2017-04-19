package ysaak.garde.gui.leftpanel;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.gui.common.view.AbstractFormView;
import ysaak.garde.service.translation.I18n;

/**
 * Left panel displaying child information
 */
public class LeftPanelView extends AbstractFormView<ChildDTO> {
  private static final double PANEL_WIDTH = 200.;
  private static final double PANEL_PADDING = 10.;

  private final BorderPane mainPane;

  private final Label firstNameLabel;
  private final Label lastNameLabel;

  private JFXButton terminateButton;

  public LeftPanelView() {
    super("");

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

    mainPane = new BorderPane();
    mainPane.setTop(topPane);
    mainPane.setBottom(bottomPane);

    mainPane.getStyleClass().add("left-panel");

    mainPane.setPadding(new Insets(PANEL_PADDING));
    mainPane.setPrefWidth(PANEL_WIDTH + 2*PANEL_PADDING);
    mainPane.setMinWidth(PANEL_WIDTH + 2*PANEL_PADDING);
  }

  @Override
  public void initialize() { /**/ }

  public void setOnTerminateAction(EventHandler<ActionEvent> value) {
    terminateButton.setOnAction(value);
  }

  @Override
  public void setData(ChildDTO child) {
    if (child != null) {
      lastNameLabel.setText(child.getLastName());
      firstNameLabel.setText(child.getFirstName());
    }
    else {
      lastNameLabel.setText(" ");
      firstNameLabel.setText(" ");
    }
  }

  @Override
  public ChildDTO getData() {
    return null;
  }

  public Node getView() {
    return mainPane;
  }
}

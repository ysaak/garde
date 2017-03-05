package ysaak.hera.nexus.gui.leftpanel;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.view.AbstractView;

/**
 * Left panel displaying child informations
 */
public class ChildLeftPanel extends AbstractView<Child> {
  private static final double PANEL_WIDTH = 200.;
  private static final double PANEL_PADDING = 10.;

  private final VBox mainPane;

  private final Label firstNameLabel;
  private final Label lastNameLabel;

  public ChildLeftPanel() {

    mainPane = new VBox();
    mainPane.setPrefWidth(PANEL_WIDTH + 2*PANEL_PADDING);
    mainPane.setMinWidth(PANEL_WIDTH + 2*PANEL_PADDING);

    mainPane.setPadding(new Insets(PANEL_PADDING));

    mainPane.getStyleClass().add("left-panel");


    ImageView childImage = new ImageView(getClass().getResource("/testimg.jpg").toExternalForm());
    lastNameLabel = new Label(" ");
    lastNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 1.2em");
    firstNameLabel = new Label(" ");

    mainPane.getChildren().addAll(childImage, lastNameLabel, firstNameLabel);
  }

  @Override
  public void initialize() {
    // By default the pane is hidden
    mainPane.setVisible(false);
  }

  @Override
  public void setData(Child child) {
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
  public Child getData() {
    return null;
  }

  public Node getView() {
    return mainPane;
  }
}

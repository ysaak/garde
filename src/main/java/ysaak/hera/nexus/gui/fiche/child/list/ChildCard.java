package ysaak.hera.nexus.gui.fiche.child.list;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.components.button.IconButton;
import ysaak.hera.nexus.gui.common.components.grid.ToggleableView;

public class ChildCard extends ToggleableView<Child> {
  private final VBox mainBox = new VBox(10.0);
  private final VBox box = new VBox(4.0);
  private final HBox buttonBox = new HBox(5.);

  private ImageView childImage;
  private final Label firstNameLabel;
  private final Label lastNameLabel;
  
  //private final String selectedString = "-fx-background-color: #ff0000; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-width: 0; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, #93948d, 10, 0, 0, 0);";
  //private final String unselectedString = "-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-width: 0; -fx-padding: 10; -fx-effect: dropshadow(three-pass-box, #93948d, 10, 0, 0, 0);";

  private final String selectedString = "-fx-background-color: #ff0000; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-width: 0;";
  private final String unselectedString = "-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-width: 0;";

  private Button addAttendanceButton;
  
  public ChildCard() {
    super();
    mainBox.setPadding(new Insets(10.));
    
    HBox.setMargin(mainBox, new Insets(0.,5.,0.,5.));
    
    childImage = new ImageView(getClass().getResource("/testimg2.png").toExternalForm());
    
    Rectangle rect = new Rectangle(150., 150.);
    rect.setArcWidth(10.);
    rect.setArcHeight(10.);
    
    childImage.setClip(rect);//new Circle(75., 75., 75.));
    
    mainBox.setAlignment(Pos.TOP_LEFT);
    box.setAlignment(Pos.TOP_LEFT);
    buttonBox.setAlignment(Pos.CENTER_RIGHT);
    
    lastNameLabel = new Label(" ");
    lastNameLabel.setStyle("-fx-font-weight: bolder; -fx-font-size: 1.2em");
    firstNameLabel = new Label(" ");
    box.getChildren().addAll(lastNameLabel, firstNameLabel);

    initButtons();
    
    mainBox.getChildren().addAll(childImage, box, buttonBox);
    selectedProperty().addListener((a, b, c) -> setSelectedStyle(c));
    
    mainBox.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> getToggleGroup().selectToggle(this));
    setSelectedStyle(false);
  }
  
  private void initButtons() {
    addAttendanceButton = new IconButton(MaterialDesignIcon.CALENDAR_PLUS);
    
    buttonBox.getChildren().add(addAttendanceButton);
  }
  
  public Button getAddAttendanceButton() {
    return addAttendanceButton;
  }
  
  protected void setSelectedStyle(boolean selected) {
    mainBox.setStyle(selected ? selectedString : unselectedString);
  }

  @Override
  public void setData(Child data) {
    this.data = data;
    
    firstNameLabel.setText(data.getFirstName());
    lastNameLabel.setText(data.getLastName());
  }

  @Override
  public Child getData() {
    return this.data;
  }

  @Override
  public Node getView() {
    return mainBox;
  }
}

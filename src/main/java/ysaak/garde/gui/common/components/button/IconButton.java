package ysaak.garde.gui.common.components.button;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

public class IconButton extends Button {
  private final Paint iconBaseColor;
  private final Paint iconHoverColor; 

  private final FontIcon textIcon;
  
  public IconButton(Material icon) {
    super("");
    getStyleClass().add("icon-button");
    
    iconBaseColor = getTextFill();
    iconHoverColor = Color.ORANGE;

    textIcon = new FontIcon(icon);
    textIcon.setIconSize(16);
    textIcon.setIconColor(iconBaseColor);
    
    setGraphic(textIcon);
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    
    setOnMouseEntered(e -> textIcon.setFill(iconHoverColor));
    setOnMouseExited(e -> textIcon.setFill(iconBaseColor));
  }
}

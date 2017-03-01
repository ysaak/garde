package ysaak.hera.nexus.gui.common.components.button;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class IconButton extends Button {
  private final Paint iconBaseColor;
  private final Paint iconHoverColor; 

  private final Text textIcon;
  
  public IconButton(MaterialDesignIcon icon) {
    super("");
    getStyleClass().add("icon-button");
    
    iconBaseColor = getTextFill();
    iconHoverColor = Color.ORANGE;

    textIcon = MaterialDesignIconFactory.get().createIcon(icon, "1.5em");
    textIcon.setFill(iconBaseColor);
    
    setGraphic(textIcon);
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    
    setOnMouseEntered(e -> textIcon.setFill(iconHoverColor));
    setOnMouseExited(e -> textIcon.setFill(iconBaseColor));
  }
}

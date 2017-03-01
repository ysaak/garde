package ysaak.hera.nexus.gui.common.components.button;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class NavbarButton extends Button {
  public static final Paint DEFAULT_ICON_BASE_COLOR = Color.WHITE;
  public static final Paint DEFAULT_ICON_HOVER_COLOR = Color.ORANGE;
  
  private Paint iconBaseColor = DEFAULT_ICON_BASE_COLOR;
  private Paint iconHoverColor = DEFAULT_ICON_HOVER_COLOR; 

  private final Text textIcon;
  
  public NavbarButton(MaterialDesignIcon icon) {
    super("");

    /*
    textIcon = new MaterialDesignIconView(icon);
    textIcon.setSize("1.5em");
    textIcon.setFill(iconBaseColor);
    */
    textIcon = MaterialDesignIconFactory.get().createIcon(icon, "1.5em");
    textIcon.setFill(iconBaseColor);

    setGraphic(textIcon);
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    
    setOnMouseEntered(e -> textIcon.setFill(iconHoverColor));
    setOnMouseExited(e -> textIcon.setFill(iconBaseColor));
    
    getStyleClass().add("navbar-button");
  }
  
  public void setIconBaseColor(Paint iconDefaultColor) {
    this.iconBaseColor = iconDefaultColor;
    textIcon.setFill(iconBaseColor);
  }
  
  public void setIconHoverColor(Paint iconHoverColor) {
    this.iconHoverColor = iconHoverColor;
  }
}

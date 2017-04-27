package ysaak.garde.gui.common;

import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

/**
 * Utilities for UI
 */
public final class UiUtils {
  private UiUtils() { /* Hidden constructor */ }

  public static FontIcon getAddIcon() {
    return new FontIcon(Material.ADD_CIRCLE_OUTLINE);
  }

  public static FontIcon getEditIcon() {
    return new FontIcon(Material.EDIT);
  }

  public static FontIcon getDeleteIcon() {
    return new FontIcon(Material.CANCEL);
  }
}

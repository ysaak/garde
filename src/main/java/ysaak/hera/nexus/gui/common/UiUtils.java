package ysaak.hera.nexus.gui.common;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.scene.text.Text;

/**
 * Utilities for UI
 */
public final class UiUtils {
  private UiUtils() { /* Hidden constructor */ }

  public static Text getAddIcon() {
    return MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.PLUS_CIRCLE);
  }

  public static Text getEditIcon() {
    return MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.PENCIL);
  }

  public static Text getDeleteIcon() {
    return MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.DELETE);
  }
}

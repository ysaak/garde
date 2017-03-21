package ysaak.hera.nexus.gui.common.buttonbar;

/**
 * Factory for default button bars
 */
public final class ButtonBarFactory {

  private ButtonBarFactory() { /* Hidden constructor */ }

  public static ButtonBar get(ButtonBarType type) {

    final ButtonBar bar;

    switch (type) {

      case DEFAULT:
        bar = new FinishButtonBar();
        break;

      case EDIT:
        bar = new EditorButtonBar();
        break;

      case NONE:
      default:
        bar = null;
    }

    return bar;
  }
}

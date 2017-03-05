package ysaak.hera.nexus.gui.common.buttonbar;

/**
 * Factory for default button bars
 */
public final class ButtonBarFactory {

  private ButtonBarFactory() { /* Hidden constructor */ }

  public static ButtonBar getFinishButtonBar() {
    return new FinishButtonBar();
  }

  public static ButtonBar getEditorButtonBar() {
    return new EditorButtonBar();
  }
}

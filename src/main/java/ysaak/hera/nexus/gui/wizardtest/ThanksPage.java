package ysaak.hera.nexus.gui.wizardtest;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ysaak.hera.nexus.gui.wizard.WizardPage;

public class ThanksPage extends WizardPage {

  public ThanksPage() {
    super("Thanks");
  }

  @Override
  public Parent getContent() {
    StackPane stack = new StackPane(new Label("Thanks!"));
    VBox.setVgrow(stack, Priority.ALWAYS);
    return stack;
  }
}

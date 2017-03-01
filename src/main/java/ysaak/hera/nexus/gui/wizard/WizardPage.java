package ysaak.hera.nexus.gui.wizard;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public abstract class WizardPage extends VBox {

  Button priorButton = new Button("_Previous");
  Button nextButton = new Button("N_ext");
  Button cancelButton = new Button("Cancel");
  Button finishButton = new Button("_Finish");

  protected WizardPage(String title) {
    Label label = new Label(title);
    label.setStyle("-fx-font-weight: bold; -fx-padding: 0 0 5 0;");
    setId(title);
    setSpacing(5);
    setStyle(
        "-fx-padding:10; -fx-background-color: honeydew; -fx-border-color: derive(honeydew, -30%); -fx-border-width: 3;");

    Region spring = new Region();
    VBox.setVgrow(spring, Priority.ALWAYS);
    getChildren().addAll(getContent(), spring, getButtons());

    priorButton.setOnAction(event -> priorPage());
    nextButton.setOnAction(event -> nextPage());
    cancelButton.setOnAction(event -> getWizard().cancel());
    finishButton.setOnAction(event -> getWizard().finish());
  }

  HBox getButtons() {
    Region spring = new Region();
    HBox.setHgrow(spring, Priority.ALWAYS);
    HBox buttonBar = new HBox(5);
    cancelButton.setCancelButton(true);
    finishButton.setDefaultButton(true);
    buttonBar.getChildren().addAll(spring, priorButton, nextButton, cancelButton, finishButton);
    return buttonBar;
  }

  public abstract Parent getContent();

  boolean hasNextPage() {
    return getWizard().hasNextPage();
  }

  boolean hasPriorPage() {
    return getWizard().hasPriorPage();
  }

  void nextPage() {
    getWizard().nextPage();
  }

  void priorPage() {
    getWizard().priorPage();
  }

  void navTo(String id) {
    getWizard().navTo(id);
  }

  Wizard getWizard() {
    return (Wizard) getParent();
  }

  public void manageButtons() {
    if (!hasPriorPage()) {
      priorButton.setDisable(true);
    }

    if (!hasNextPage()) {
      nextButton.setDisable(true);
    }
  }
}

package ysaak.hera.nexus.gui.fiche.child.root;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import ysaak.hera.nexus.gui.common.ContextBuilder;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;
import ysaak.hera.nexus.service.translation.I18n;

public class ChildRootView extends AbstractFormView<Long> {
  private BorderPane pane;

  public ChildRootView() {
    super(I18n.get("child.root.title"));
  }

  @Override
  public void initialize() {

    FlowPane actionsListPane = new FlowPane();
    actionsListPane.setVgap(10.);
    actionsListPane.setHgap(10.);
    actionsListPane.setAlignment(Pos.TOP_CENTER);

    actionsListPane.getChildren().addAll(
            createActionButton("Rapport mensuel", MaterialDesignIcon.CALENDAR, "MONTHLY-VIEW"),
            createActionButton("Présences", MaterialDesignIcon.ACCOUNT, "ATTENDANCE-LIST"),
            createActionButton("Edit", MaterialDesignIcon.PENCIL, "CHILD-EDIT"),
            createActionButton("attList", MaterialDesignIcon.CALENDAR, "MONTHLY-VIEW"),
            createActionButton("attList", MaterialDesignIcon.CALENDAR, "MONTHLY-VIEW"),
            createActionButton("attList", MaterialDesignIcon.CALENDAR, "MONTHLY-VIEW"),
            createActionButton("attList", MaterialDesignIcon.CALENDAR, "MONTHLY-VIEW"),
            createActionButton("attList", MaterialDesignIcon.CALENDAR, "MONTHLY-VIEW")
    );


    TitledPane actionsPane = new TitledPane(I18n.get("child.root.actionsList"), actionsListPane);
    actionsPane.getStyleClass().add("card");

    pane = new BorderPane(actionsPane);
  }

  private Button createActionButton(String text, MaterialDesignIcon icon, String viewCode) {
    JFXButton button = new JFXButton(text, MaterialDesignIconFactory.get().createIcon(icon, "4em"));
    button.setButtonType(JFXButton.ButtonType.RAISED);
    button.setContentDisplay(ContentDisplay.TOP);
    button.setStyle("-fx-background-color: -light-primary-color");
    button.setOnAction(event -> openForm(viewCode));
    return button;
  }

  private void openForm(String viewCode) {
    fireOpenFormRequest(viewCode, ContextBuilder.get().withChildId(originalData).build());
  }

  @Override
  public Node getView() {
    return pane;
  }

  @Override
  public void setData(Long data) {
    this.originalData = data;
  }

  @Override
  public Long getData() {
    return originalData;
  }
}

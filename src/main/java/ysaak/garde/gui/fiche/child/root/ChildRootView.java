package ysaak.garde.gui.fiche.child.root;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;
import ysaak.garde.gui.common.ContextBuilder;
import ysaak.garde.gui.common.view.AbstractFormView;
import ysaak.garde.gui.fiche.child.root.widget.ContractWidget;
import ysaak.garde.service.translation.I18n;

public class ChildRootView extends AbstractFormView<Long> {
  private BorderPane pane;

  private ContractWidget contractWidget;

  public ChildRootView() {
    super(I18n.get("child.root.title"));
  }

  @Override
  public void initialize() {

    contractWidget = new ContractWidget();


    FlowPane actionsListPane = new FlowPane();
    actionsListPane.setVgap(10.);
    actionsListPane.setHgap(10.);
    actionsListPane.setAlignment(Pos.TOP_CENTER);

    actionsListPane.getChildren().addAll(
            createActionButton("Rapport mensuel", Material.INSERT_INVITATION, "MONTHLY-VIEW"),
            createActionButton("Présences", Material.EVENT, "ATTENDANCE-LIST"),
            createActionButton("Edit", Material.EDIT, "CHILD-EDIT"),
            createActionButton("attList", Material.INSERT_INVITATION, "MONTHLY-VIEW"),
            createActionButton("attList", Material.INSERT_INVITATION, "MONTHLY-VIEW")
    );


    TitledPane actionsPane = new TitledPane(I18n.get("child.root.actionsList"), actionsListPane);
    actionsPane.getStyleClass().add("card");

    pane = new BorderPane(actionsPane);
    pane.setTop(new HBox(contractWidget.getView()));
  }

  private Button createActionButton(String text, Material icon, String viewCode) {
    FontIcon iconFont = new FontIcon(icon);
    iconFont.setIconSize(24);
    JFXButton button = new JFXButton(text, iconFont);
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

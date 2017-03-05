package ysaak.hera.nexus.gui.fiche.root;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.apache.commons.lang3.StringUtils;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.ContextBuilder;
import ysaak.hera.nexus.gui.common.Contexts;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.gui.common.buttonbar.CustomButtonBar;
import ysaak.hera.nexus.gui.common.components.grid.SelectableGridView;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;
import ysaak.hera.nexus.service.translation.I18n;

import java.util.List;

public class RootView extends AbstractFormView<List<Child>> {
  private BorderPane pane;

  private TextField filter = new TextField();

  private final ObservableList<Child> list = FXCollections.observableArrayList();
  private FilteredList<Child> filteredData = null;

  @Override
  public String getTitle() {
    return I18n.get("root.title");
  }

  @Override
  public ButtonBar getButtonBar() {
    CustomButtonBar bar = new CustomButtonBar();

    JFXButton parametersButton = new JFXButton(I18n.get("root.actions.parameters"), MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.SETTINGS));
    parametersButton.setOnAction(evt -> displayParametersDialog());

    JFXButton birthdayButton = new JFXButton(I18n.get("root.actions.birthdays"), MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.CAKE_VARIANT));
    birthdayButton.setOnAction(evt -> showBirthdayDrawer());

    // TODO remove raised state
    birthdayButton.setButtonType(JFXButton.ButtonType.RAISED);
    birthdayButton.setStyle("-fx-background-color: -light-primary-color");

    bar.setRightNodes(birthdayButton, parametersButton);
    return bar;
  }

  @Override
  public void initialize() {
    filter.setPromptText(I18n.get("common.filter"));
    
    filteredData = new FilteredList<>(list, p -> true);

    SelectableGridView<Child> menuItems = new SelectableGridView<>(filteredData, i -> {
      ChildCard card = new ChildCard();
      card.getAddAttendanceButton().setOnAction(e -> fireOpenFormRequest("ATTENDANCE-LIST", Contexts.idContext(card.getData().getId())));

      return card;
    });

    menuItems.setCellSize(ChildCard.CARD_WIDTH, ChildCard.CARD_HEIGHT);
    
    menuItems.setOnMouseReleased(e -> {
      if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() > 1) {
        Child child = (Child) e.getSource();
        fireOpenFormRequest("MONTHLY-VIEW", ContextBuilder.get().withId(child.getId()).build());


      }
    });

    //////
    
    final HBox filterBar = new HBox(5.0, filter);
    filterBar.setAlignment(Pos.CENTER_LEFT);
    
    filterBar.setPadding(new Insets(0, 0, 10.0, 0));

    pane = new BorderPane(menuItems.getView());
    pane.setTop(filterBar);
    
    filter.textProperty().addListener((observable, oldValue, newValue) -> filter());
  }

  @Override
  public Node getView() {
    return pane;
  }

  private void filter() {
    if (filteredData == null) {
      return;
    }
    
    String genericText = filter.getText();
    
    filteredData.setPredicate(app -> {
      boolean match = StringUtils.containsIgnoreCase(app.getFirstName(), genericText);
      match |= StringUtils.containsIgnoreCase(app.getLastName(), genericText);
      return match;
    });
  }

  @Override
  public void setData(List<Child> data) {
    list.clear();
    list.addAll(data);
  }

  private void displayParametersDialog() {
    System.err.println("TODO displayParametersDialog");
  }

  private void showBirthdayDrawer() {
    System.err.println("TODO displayParametersDialog");
  }

  @Override
  public List<Child> getData() {
    return null;
  }
}

package ysaak.hera.nexus.gui.fiche.root;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.ContextBuilder;
import ysaak.hera.nexus.gui.common.Contexts;
import ysaak.hera.nexus.gui.common.buttonbar.Action;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.gui.common.buttonbar.CustomButtonBar;
import ysaak.hera.nexus.gui.common.components.grid.SelectableGridView;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;

import java.util.Arrays;
import java.util.List;

public class RootView extends AbstractFormView<List<Child>> {
  private BorderPane pane;

  private TextField filter = new TextField();

  private final ObservableList<Child> list = FXCollections.observableArrayList();
  private FilteredList<Child> filteredData = null;

  @Override
  public String getTitle() {
    return translationFacade.get("root.title");
  }

  @Override
  public ButtonBar getButtonBar() {
    CustomButtonBar bar = new CustomButtonBar();
    bar.setCustomStyles(Arrays.asList("hbtn-flat"));
    bar.setRightActions(new ParametersAction());
    return bar;
  }

  @Override
  public void initialize() {
    filter.setPromptText(translationFacade.get("common.filter"));
    
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

  private void onParametersAction() {
    System.err.println("TODO");
  }

  @Override
  public List<Child> getData() {
    return null;
  }

  private class ParametersAction extends Action {
    private ParametersAction() {
      super(translationFacade.get("root.actions.parameters"));

      Text icon = MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.SETTINGS);
      setGraphic(icon);
    }

    @Override
    public void handle(ActionEvent event) {
      onParametersAction();
    }
  }
}

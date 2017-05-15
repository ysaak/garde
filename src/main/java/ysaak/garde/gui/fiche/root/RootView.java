package ysaak.garde.gui.fiche.root;

import com.jfoenix.controls.JFXButton;
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
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.ContextBuilder;
import ysaak.garde.gui.common.Contexts;
import ysaak.garde.gui.common.components.grid.SelectableGridView;
import ysaak.garde.gui.common.view.AbstractFormView;
import ysaak.garde.service.translation.I18n;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RootView extends AbstractFormView<List<ChildDTO>> {
  private BorderPane pane;

  private TextField filter = new TextField();

  private final ObservableList<ChildDTO> list = FXCollections.observableArrayList();
  private FilteredList<ChildDTO> filteredData = null;

  public RootView() {
    super(I18n.get("root.title"));
  }

  @Override
  public List<Node> getToolbarComponents() {


    List<ToolbarItem> items = Arrays.asList(
      new ToolbarItem("CONTRACT-CREATE", Material.ADD_CIRCLE_OUTLINE, "root.actions.createChild"),
      new ToolbarItem(null, Material.CAKE, "root.actions.birthdays"),
      new ToolbarItem("PARAMETERS", Material.SETTINGS, "root.actions.parameters")
    );

    List<Node> components = new ArrayList<>(items.size());

    for (ToolbarItem item : items) {

      JFXButton button = new JFXButton(I18n.get(item.text), new FontIcon(item.icon));
      button.setOnAction(evt -> executorToolbarAction(item.formCode));
      components.add(button);
    }

    return components;
  }

  @Override
  public void initialize() {
    filter.setPromptText(I18n.get("common.filter"));
    
    filteredData = new FilteredList<>(list, p -> true);

    SelectableGridView<ChildDTO> menuItems = new SelectableGridView<>(filteredData, i -> {
      ChildCard card = new ChildCard();
      card.getAddAttendanceButton().setOnAction(e -> fireOpenFormRequest("ATTENDANCE-LIST", Contexts.idContext(card.getData().getId())));

      return card;
    });

    menuItems.setCellSize(ChildCard.CARD_WIDTH, ChildCard.CARD_HEIGHT);
    
    menuItems.setOnMouseReleased(e -> {
      if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() > 1) {
        ChildDTO child = (ChildDTO) e.getSource();
        fireOpenFormRequest("CHILD-ROOT", ContextBuilder.get().withId(child.getId()).build());
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
  public void setData(List<ChildDTO> data) {
    list.clear();
    list.addAll(data);
  }

  private void executorToolbarAction(String formCode) {
    if (formCode != null) {
      fireOpenFormRequest(formCode, Context.EMPTY);
    }
    else {
      // TODO remove temporary message
      System.err.println("TODO");
    }
  }

  @Override
  public List<ChildDTO> getData() {
    return null;
  }

  private class ToolbarItem {
    public final String formCode;
    public final Ikon icon;
    public final String text;

    ToolbarItem(String formCode, Ikon icon, String text) {
      this.formCode = formCode;
      this.icon = icon;
      this.text = text;
    }
  }
}

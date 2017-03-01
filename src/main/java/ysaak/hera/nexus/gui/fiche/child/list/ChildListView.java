package ysaak.hera.nexus.gui.fiche.child.list;

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
import ysaak.hera.nexus.gui.common.components.grid.SelectableGridView;
import ysaak.hera.nexus.gui.common.view.AbstractView;

import java.util.List;

public class ChildListView extends AbstractView<List<Child>> {
  private BorderPane pane;

  private TextField filter = new TextField();

  private final ObservableList<Child> list = FXCollections.observableArrayList();
  private FilteredList<Child> filteredData = null;
  

  public ChildListView() {
    filter.setPromptText("Filter");
    
    filteredData = new FilteredList<>(list, p -> true);

    SelectableGridView<Child> menuItems = new SelectableGridView<>(filteredData, i -> {
      ChildCard card = new ChildCard();
      card.getAddAttendanceButton().setOnAction(e -> fireOpenFormRequest("ATTENDANCE-LIST", Contexts.idContext(card.getData().getId())));

      return card;
    });
    
    
    menuItems.setOnMouseReleased(e -> {
      if (e.getButton().equals(MouseButton.PRIMARY) && e.getClickCount() > 1) {
        Child child = (Child) e.getSource();
        fireOpenFormRequest("MONTHLY-VIEW", ContextBuilder.get().withId(child.getId()).build());
      }
    });
    
    HBox toolbar = new HBox(5.0, filter);
    toolbar.setAlignment(Pos.CENTER_LEFT);
    
    toolbar.setPadding(new Insets(0, 0, 10.0, 0));
    
    pane = new BorderPane(menuItems.getView());
    pane.setTop(toolbar);
    
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
      boolean match = false;
      match |= StringUtils.containsIgnoreCase(app.getFirstName(), genericText);
      match |= StringUtils.containsIgnoreCase(app.getLastName(), genericText);
      return match;
    });
  }

  @Override
  public void setData(List<Child> data) {
    list.clear();
    list.addAll(data);
  }

  @Override
  public List<Child> getData() {
    return null;
  }
}

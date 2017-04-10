package ysaak.garde.gui.common.components.grid;

import java.util.HashMap;
import java.util.Map;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class SelectableGridView<T> {

  private GridView<T> gridview;
  
  private ToggleGroup group = new ToggleGroup();
  private final ObjectProperty<T> selectedProperty = new SimpleObjectProperty<>(null);
  
  private final Map<T, ToggleableView<T>> viewMap = new HashMap<>();
  private final Callback<T, ToggleableView<T>> viewCallback;
  
  private EventHandler<MouseEvent> mouseReleasedEventHandler = null;
  
  private final EventHandler<MouseEvent> localMouseEventHandler; 
  
  public SelectableGridView(ObservableList<T> list, Callback<T, ToggleableView<T>> viewCallback) {
    this.viewCallback = viewCallback;
    
    gridview = new GridView<>(list);
    gridview.setCellWidth(160.);
    gridview.setCellHeight(250.);
    gridview.setCellFactory(p -> new SelectableGridCell());
    
    localMouseEventHandler = event -> {
      if (mouseReleasedEventHandler != null) {
        MouseEvent evt2 = MouseEvent.copyForMouseDragEvent(event, getSelectedItem(), event.getTarget(), null, event.getSource(), event.getPickResult());
        mouseReleasedEventHandler.handle(evt2);
      }
    };
    
    group.selectedToggleProperty().addListener((o, oldV, newV) -> selectedProperty.set(getSelectedItem()));
  }

  public void setCellSize(double width, double height) {
    gridview.setCellWidth(width);
    gridview.setCellHeight(height);
  }
  
  public void setOnMouseReleased(EventHandler<MouseEvent> mouseReleasedEventHandler) {
    this.mouseReleasedEventHandler = mouseReleasedEventHandler;
  }

  public GridView<T> getView() {
    return gridview;
  }
  
  private ToggleableView<T> createView(T item) {
    ToggleableView<T> view = viewCallback.call(item);
    view.setData(item);
    view.getView().setOnMouseReleased(localMouseEventHandler);
    
    group.getToggles().add(view);
    return view;
  }
  
  @SuppressWarnings("unchecked")
  public T getSelectedItem() {
    Toggle selected = group.getSelectedToggle();
    
    if (selected != null) {
      return ((ToggleableView<T>) selected).getData();
    }
    
    return null;
  }
  
  /*
  public final ReadOnlyObjectProperty<T> selectedProperty() {
    return selectedProperty;
  }
  */
  
  private class SelectableGridCell extends GridCell<T> {
    @Override
    protected void updateItem(T item, boolean empty) {
      super.updateItem(item, empty);
      setText(null);
      
      if (empty || item == null) {
        setGraphic(null);
      }
      else {
        ToggleableView<T> view = viewMap.computeIfAbsent(item, k -> createView(item));

        setGraphic(view.getView());
      }
    }
  }
}

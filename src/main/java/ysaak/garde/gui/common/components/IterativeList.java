package ysaak.garde.gui.common.components;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;
import ysaak.garde.gui.common.view.AbstractView;
import ysaak.garde.service.translation.I18n;

import java.util.ArrayList;
import java.util.List;

public class IterativeList<T> extends BorderPane {
  private final JFXButton addButton;
  
  private final VBox centerBox;
  
  private Callback<Void, AbstractView<T>> lineFactory = null;
  
  private final List<AbstractView<T>> viewList; 
  
  private int maxLinesCount = 0;
  
  public IterativeList() {
    super();
    viewList = new ArrayList<>();
    
    centerBox = new VBox(10.0);
    centerBox.setPadding(new Insets(0., 0., 10., 0.));
    setCenter(centerBox);

    final FontIcon icon = new FontIcon(Material.ADD_CIRCLE);
    addButton = new JFXButton(I18n.get("button.add"), icon);
    addButton.setOnAction(this::addLine);
    setBottom(addButton);
  }
  
  public void setLineFactory(Callback<Void, AbstractView<T>> lineFactory) {
    this.lineFactory = lineFactory;
  }
  
  public void setMaxLinesCount(int maxLinesCount) {
    this.maxLinesCount = maxLinesCount;
    
    handleAddButtonActivation();
  }
  
  private AbstractView<T> addLine(ActionEvent evt) {
    if (lineFactory == null)
      return null;
    
    final AbstractView<T> newLine = lineFactory.call(null);
    viewList.add(newLine);
    
    final BorderPane linePane = new BorderPane(newLine.getView());

    HBox removeBtnBox = new HBox();
    removeBtnBox.setPadding(new Insets(0., 5., 0., 5.));
    linePane.setRight(removeBtnBox);

    final FontIcon icon = new FontIcon(Material.CANCEL);
    JFXButton removeButton = new JFXButton("", icon);
    removeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    
    removeButton.setOnAction(revt -> {
      viewList.remove(newLine);
      centerBox.getChildren().remove(linePane);
      handleAddButtonActivation();
    });
    removeBtnBox.getChildren().add(removeButton);
    
    centerBox.getChildren().add(linePane);
    
    handleAddButtonActivation();
    
    return newLine;
  }
  
  public List<T> getData() {
    List<T> data = new ArrayList<>();
    
    for (AbstractView<T> view : viewList) {
      data.add(view.getData());
    }

    return data;
  }
  
  public void setData(List<T> data) {
    for (T d : data) {
      AbstractView<T> view = addLine(null);
      if (view != null)
        view.setData(d);
    }
  }
  
  private void handleAddButtonActivation() {
    if (maxLinesCount > 0 && viewList.size() >= maxLinesCount) {
      addButton.setDisable(true);
    }
    else {
      addButton.setDisable(false);
    }
  }
}

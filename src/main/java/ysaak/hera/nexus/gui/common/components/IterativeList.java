package ysaak.hera.nexus.gui.common.components;

import java.util.ArrayList;
import java.util.List;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.utils.MaterialDesignIconFactory;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import ysaak.hera.nexus.gui.common.view.AbstractView;

public class IterativeList<T> extends BorderPane {
  private final Button addButton;
  
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

    final Text icon = MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.PLUS_BOX);
    addButton = new Button("Ajouter", icon);
    addButton.getStyleClass().add("hbtn");
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
  
  protected AbstractView<T> addLine(ActionEvent evt) {
    if (lineFactory == null)
      return null;
    
    final AbstractView<T> newLine = lineFactory.call(null);
    viewList.add(newLine);
    
    final BorderPane linePane = new BorderPane(newLine.getView());

    HBox removeBtnBox = new HBox();
    removeBtnBox.setPadding(new Insets(0., 5., 0., 5.));
    linePane.setRight(removeBtnBox);


    Button removeButton = new Button("", MaterialDesignIconFactory.get().createIcon(MaterialDesignIcon.MINUS_BOX));
    removeButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    removeButton.getStyleClass().add("hbtn");
    
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
      view.setData(d);
    }
  }
  
  protected void handleAddButtonActivation() {
    if (maxLinesCount > 0 && viewList.size() >= maxLinesCount) {
      addButton.setDisable(true);
    }
    else {
      addButton.setDisable(false);
    }
  }
}

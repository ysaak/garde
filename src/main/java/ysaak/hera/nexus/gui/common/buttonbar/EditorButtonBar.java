package ysaak.hera.nexus.gui.common.buttonbar;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class EditorButtonBar extends ButtonBar {
  
  private Button saveButton;
  
  private Button cancelButton;
  
  public EditorButtonBar() {
    super();

    saveButton = new Button("Save");
    saveButton.setDefaultButton(true);
    saveButton.getStyleClass().add("hbtn");
    saveButton.setOnAction(evt -> fireEvent(ButtonBarAction.SAVE));
    
    cancelButton = new Button("Cancel");
    cancelButton.setCancelButton(true);
    cancelButton.getStyleClass().add("hbtn");
    cancelButton.setOnAction(evt -> fireEvent(ButtonBarAction.CANCEL));
    
    setLeft(cancelButton);
    setRight(saveButton);
  }

  public void setOnSaveAction(EventHandler<ActionEvent> value) {
    saveButton.setOnAction(value);
  }
  
  public void setOnCancelAction(EventHandler<ActionEvent> value) {
    cancelButton.setOnAction(value);
  }
}

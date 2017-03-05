package ysaak.hera.nexus.gui.common.buttonbar;

import com.jfoenix.controls.JFXButton;

public class EditorButtonBar extends ButtonBar {

  public EditorButtonBar() {
    super();

    // FIXME translations

    JFXButton saveButton = new JFXButton("Save");
    saveButton.setDefaultButton(true);
    saveButton.setButtonType(JFXButton.ButtonType.RAISED);
    saveButton.setOnAction(evt -> fireEvent(ButtonBarAction.SAVE));

    JFXButton cancelButton = new JFXButton("Cancel");
    cancelButton.setCancelButton(true);
    cancelButton.setOnAction(evt -> fireEvent(ButtonBarAction.CANCEL));
    
    setLeft(cancelButton);
    setRight(saveButton);
  }
}

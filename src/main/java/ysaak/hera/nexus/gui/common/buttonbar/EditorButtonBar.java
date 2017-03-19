package ysaak.hera.nexus.gui.common.buttonbar;

import com.jfoenix.controls.JFXButton;
import ysaak.hera.nexus.service.translation.I18n;

public class EditorButtonBar extends ButtonBar {

  public EditorButtonBar() {
    super();

    JFXButton saveButton = new JFXButton(I18n.get("button.save"));
    saveButton.setDefaultButton(true);
    saveButton.getStyleClass().add("btn-raised");
    saveButton.setButtonType(JFXButton.ButtonType.RAISED);
    saveButton.setOnAction(evt -> fireEvent(ButtonBarAction.SAVE));

    JFXButton cancelButton = new JFXButton(I18n.get("button.cancel"));
    cancelButton.setCancelButton(true);
    cancelButton.setOnAction(evt -> fireEvent(ButtonBarAction.CANCEL));
    
    setLeft(cancelButton);
    setRight(saveButton);
  }
}

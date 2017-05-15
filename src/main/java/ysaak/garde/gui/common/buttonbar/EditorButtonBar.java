package ysaak.garde.gui.common.buttonbar;

import com.jfoenix.controls.JFXButton;
import javafx.beans.binding.Bindings;
import ysaak.garde.service.translation.I18n;

class EditorButtonBar extends ButtonBar {

  protected EditorButtonBar() {
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

    saveButton.disableProperty().bind(Bindings.not(isValidProperty()));
  }
}

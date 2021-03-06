package ysaak.garde.gui.common.buttonbar;

import com.jfoenix.controls.JFXButton;

class FinishButtonBar extends ButtonBar {

  public FinishButtonBar() {
    super();

    // FIXME translations
    JFXButton finishButton = new JFXButton("Finish");
    finishButton.setStyle("-fx-background-color: #FFF");
    finishButton.setButtonType(JFXButton.ButtonType.RAISED);
    finishButton.setOnAction(evt -> fireEvent(ButtonBarAction.FINISH));
    
    setRight(finishButton);
  }
}

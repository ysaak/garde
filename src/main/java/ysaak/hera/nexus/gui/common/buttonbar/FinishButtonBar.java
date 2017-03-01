package ysaak.hera.nexus.gui.common.buttonbar;

import javafx.scene.control.Button;

public class FinishButtonBar extends ButtonBar {
  
  private Button finishButton;
  
  public FinishButtonBar() {
    super();
    
    finishButton = new Button("Finish");
    finishButton.getStyleClass().add("hbtn");
    finishButton.setOnAction(evt -> fireEvent(ButtonBarAction.FINISH));
    
    setRight(finishButton);
  }
}

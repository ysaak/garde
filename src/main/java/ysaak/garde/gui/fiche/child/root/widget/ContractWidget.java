package ysaak.garde.gui.fiche.child.root.widget;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ysaak.garde.data.contract.ContractDTO;
import ysaak.garde.gui.common.GridFormHelper;
import ysaak.garde.gui.common.components.Card;

/**
 * Contract widget
 */
public class ContractWidget extends Card {

  private final Label startDate;

  public ContractWidget() {
    super();

    final BorderPane pane = new BorderPane();

    //pane.setHgap(10.);
    //pane.setVgap(10.);

    GridFormHelper grid = new GridFormHelper(2);

    startDate = new Label("01/01/2016");
    grid.addComponent("Début du contrat", startDate);

    pane.setCenter(grid.getPane());


    //pane.add(typeLabel, 0, 0, 2, 1);


    setTitle("Contrat occasionnel");
    setCenter(pane);
  }

  public void setData(ContractDTO contractDTO) {

  }
}

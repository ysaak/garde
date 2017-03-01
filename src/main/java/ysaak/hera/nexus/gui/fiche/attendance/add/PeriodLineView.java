package ysaak.hera.nexus.gui.fiche.attendance.add;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;
import ysaak.hera.nexus.gui.common.view.AbstractView;

public class PeriodLineView extends AbstractView<AttendancePeriod> {

  private HBox rootNode;
  
  private TextField startField;
  
  private TextField endField;

  @Override
  public void initialize() {

    startField = new TextField();
    endField = new TextField();
    
    rootNode = new HBox(10.,
        new Label("Présence de"),
        startField,
        new Label("à"),
        endField
    );
  }

  @Override
  public Node getView() {
    return rootNode;
  }

  @Override
  public void setData(AttendancePeriod data) {
    startField.setText(data.getStartHour().toString());
    endField.setText(data.getEndHour().toString());
  }

  @Override
  public AttendancePeriod getData() {
    // TODO Auto-generated method stub
    return null;
  }

}

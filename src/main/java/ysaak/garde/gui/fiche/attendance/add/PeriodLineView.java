package ysaak.garde.gui.fiche.attendance.add;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ysaak.garde.data.attendance.AttendancePeriodDTO;
import ysaak.garde.gui.common.view.AbstractView;
import ysaak.garde.service.translation.I18n;

import java.time.LocalTime;

public class PeriodLineView extends AbstractView<AttendancePeriodDTO> {

  private HBox rootNode;
  
  private TextField startField;
  
  private TextField endField;

  @Override
  public void initialize() {

    startField = new TextField();
    startField.setMaxWidth(100.);
    endField = new TextField();
    endField.setMaxWidth(100.);
    
    rootNode = new HBox(10.,
        new Label(I18n.get("attendance.edit.periods.from")),
        startField,
        new Label(I18n.get("attendance.edit.periods.to")),
        endField
    );
    rootNode.setAlignment(Pos.CENTER_LEFT);
  }

  @Override
  public Node getView() {
    return rootNode;
  }

  @Override
  public void setData(AttendancePeriodDTO data) {
    originalData = data;

    if (data != null) {
      startField.setText(data.getStartHour().toString());
      endField.setText(data.getEndHour().toString());
    }
    else {
      startField.setText("");
      endField.setText("");
    }
  }

  @Override
  public AttendancePeriodDTO getData() {
    AttendancePeriodDTO period = (originalData != null) ? originalData : new AttendancePeriodDTO();
    period.setStartHour(parseTime(startField.getText()));
    period.setEndHour(parseTime(endField.getText()));
    return period;
  }

  private LocalTime parseTime(String text) {
    if (text != null) {
      return LocalTime.parse(text);
    }
    return null;
  }
}

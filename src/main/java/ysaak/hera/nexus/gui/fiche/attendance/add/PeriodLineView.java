package ysaak.hera.nexus.gui.fiche.attendance.add;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import ysaak.hera.nexus.data.attendance.AttendancePeriod;
import ysaak.hera.nexus.gui.common.view.AbstractView;
import ysaak.hera.nexus.service.translation.I18n;

import java.time.LocalTime;

public class PeriodLineView extends AbstractView<AttendancePeriod> {

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
  public void setData(AttendancePeriod data) {
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
  public AttendancePeriod getData() {
    AttendancePeriod period = (originalData != null) ? originalData : new AttendancePeriod();
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

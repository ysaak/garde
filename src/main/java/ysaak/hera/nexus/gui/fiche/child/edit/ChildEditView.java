package ysaak.hera.nexus.gui.fiche.child.edit;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.Formatters;
import ysaak.hera.nexus.gui.common.GridFormHelper;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBarFactory;
import ysaak.hera.nexus.gui.common.components.Card;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;
import ysaak.hera.nexus.service.translation.I18n;

import java.time.LocalDate;

public class ChildEditView extends AbstractFormView<Child> {

  private VBox mainPane;

  private TextField lastNameField;
  private TextField firstNameField;

  private DatePicker birthdayPicker;
  private Label ageLabel;

  private TextArea sicknessField;

  private TextArea commentsField;

  public ChildEditView() {
    super(I18n.get("child.edit.title"));
  }

  @Override
  public ButtonBar getButtonBar() {
    return ButtonBarFactory.getEditorButtonBar();
  }

  @Override
  public void initialize() {

    lastNameField = new JFXTextField();
    firstNameField = new JFXTextField();

    birthdayPicker = new JFXDatePicker();
    ageLabel = new Label("");
    birthdayPicker.setOnAction(event -> updateAgeLabel());

    Card baseCard = initializeBaseCard();

    sicknessField = new JFXTextArea();
    sicknessField.setPrefRowCount(5);
    Card sicknessCard = new Card(I18n.get("child.edit.sickness"), sicknessField);


    commentsField = new JFXTextArea();
    commentsField.setPrefRowCount(5);
    Card commentsCard = new Card(I18n.get("child.edit.comments"), commentsField);

    mainPane = new VBox(20, baseCard.getView(), sicknessCard.getView(), commentsCard.getView());
  }

  private Card initializeBaseCard() {

    GridFormHelper helper = new GridFormHelper(2);

    helper.addComponent(I18n.get("child.edit.main.lastName"), lastNameField, true);
    helper.addComponent(I18n.get("child.edit.main.firstName"), firstNameField, true);


    HBox birthdayPane = new HBox(5, birthdayPicker, ageLabel);
    birthdayPane.setAlignment(Pos.CENTER_LEFT);

    helper.addComponent(I18n.get("child.edit.main.birthday"), birthdayPane);


    return new Card(I18n.get("child.edit.main"), helper.getPane());
  }

  private void updateAgeLabel() {
    LocalDate birthday = birthdayPicker.getValue();
    if (birthday == null) {
      ageLabel.setText("");
    }
    else {
      ageLabel.setText("(" + Formatters.formatAge(birthday) + ")");
    }
  }

  @Override
  public Node getView() {
    return mainPane;
  }

  @Override
  public void setData(Child data) {
    originalData = data;
    if (data != null) {
      setTitle(I18n.get("child.edit.title.update"));

      lastNameField.setText(data.getLastName());
      firstNameField.setText(data.getFirstName());
      birthdayPicker.setValue(data.getBirthDate());

      sicknessField.setText(data.getSickness());
      commentsField.setText(data.getComments());
    }
    else {
      setTitle("child.edit.title");
    }
  }

  @Override
  public Child getData() {
    Child data = (originalData != null) ? originalData : new Child();

    data.setLastName(lastNameField.getText());
    data.setFirstName(firstNameField.getText());
    data.setBirthDate(birthdayPicker.getValue());
    data.setSickness(sicknessField.getText());
    data.setComments(commentsField.getText());

    return data;
  }
}

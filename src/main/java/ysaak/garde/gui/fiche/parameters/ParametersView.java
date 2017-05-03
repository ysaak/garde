package ysaak.garde.gui.fiche.parameters;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.converter.CurrencyStringConverter;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.data.parameter.ParameterType;
import ysaak.garde.data.parameter.Parameters;
import ysaak.garde.gui.common.GridFormHelper;
import ysaak.garde.gui.common.components.Card;
import ysaak.garde.gui.common.view.AbstractFormView;
import ysaak.garde.service.translation.I18n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ParametersView extends AbstractFormView<List<ParameterDTO>> {

  private VBox mainPane;

  private TextField baseHourField;
  private TextField increasedHourField;

  private final Map<String, TextField> parametersField;
  private final Map<String, ParameterDTO> parameterMap;

  public ParametersView() {
    super(I18n.get("parameters.title"));
    parameterMap = new HashMap<>();
    parametersField = new HashMap<>();
  }

  @Override
  public void initialize() {
    final CurrencyStringConverter csc = new CurrencyStringConverter();
    baseHourField = new TextField();
    baseHourField.setTextFormatter(new TextFormatter<>(csc, 0.));
    parametersField.put(Parameters.CONTRACT_BASE_HOUR_VALUE, baseHourField);

    increasedHourField = new TextField();
    increasedHourField.setTextFormatter(new TextFormatter<>(csc, 0.));
    parametersField.put(Parameters.CONTRACT_INCREASED_HOUR_VALUE, increasedHourField);
    Card contractCard = initContractSection();


    mainPane = new VBox(20);
    mainPane.setFillWidth(true);
    mainPane.getChildren().addAll(contractCard.getView());
  }

  private Card initContractSection() {
    final GridFormHelper helper = new GridFormHelper(2);
    helper.addComponent(I18n.get("parameters.contractSection.baseHourPrice"), baseHourField, true);
    helper.addComponent(I18n.get("parameters.contractSection.increasedHourPrice"), increasedHourField, true);

    return new Card(I18n.get("parameters.contractSection.title"), helper.getPane());
  }

  @Override
  public Node getView() {
    return mainPane;
  }

  @Override
  public void setData(List<ParameterDTO> data) {
    parameterMap.clear();

    if (data != null) {
      for (ParameterDTO param : data) {
        parameterMap.put(param.getCode(), param);
      }
    }

    // Populate fields
    for (Map.Entry<String, TextField> entry : parametersField.entrySet()) {
      setField(entry.getKey(), entry.getValue());
    }
  }

  @SuppressWarnings("unchecked")
  private void setField(String code, TextField field) {

    ParameterDTO parameter = parameterMap.get(code);

    if (parameter != null) {
      if ((parameter.getType() == ParameterType.DOUBLE || parameter.getType() == ParameterType.INTEGER)
              && field.getTextFormatter() != null) {
        ((TextFormatter<Number>) field.getTextFormatter()).setValue(parameter.getDoubleValue());
      }
      else {
        field.setText(parameterMap.get(code).getValue());
      }
    }
    else {
      field.setText("");
    }
  }

  @Override
  public List<ParameterDTO> getData() {
    List<ParameterDTO> parameters = new ArrayList<>();

    // Populate fields
    for (Map.Entry<String, TextField> entry : parametersField.entrySet()) {

      ParameterDTO parameter = parameterMap.get(entry.getKey());

      String newValue = getFieldValue(parameter.getType(), entry.getValue());

      if (!Objects.equals(parameter.getValue(), newValue)) {
        // Values differs, store the new one
        parameter.setValue(newValue);

        parameters.add(parameter);
      }
    }

    return parameters;
  }

  @SuppressWarnings("unchecked")
  private String getFieldValue(ParameterType type, TextField field) {
    final String result;

    if ((type == ParameterType.DOUBLE || type == ParameterType.INTEGER) && field.getTextFormatter() != null) {
      Number number  = ((TextFormatter<Number>) field.getTextFormatter()).getValue();
      result = (number != null) ? number.toString() : "";
    }
    else {
      result = field.getText();
    }

    return result;
  }
}

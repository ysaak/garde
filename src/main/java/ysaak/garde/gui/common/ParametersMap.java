package ysaak.garde.gui.common;

import javafx.scene.control.TextField;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.data.parameter.ParameterType;
import ysaak.garde.gui.common.components.fields.MonetaryField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class when managing parameters
 */
public class ParametersMap {

  private final Map<String, ParameterDTO> parameterMap = new HashMap<>();

  public void setParameters(List<ParameterDTO> parameters) {
    parameterMap.clear();

    if (parameters != null) {
      for (ParameterDTO param : parameters) {
        parameterMap.put(param.getCode(), param);
      }
    }
  }

  public ParameterDTO get(String key) {
    return parameterMap.get(key);
  }

  @SuppressWarnings("unchecked")
  public void setField(String code, TextField field) {
    ParameterDTO parameter = parameterMap.get(code);

    if (parameter != null) {
      if (parameter.getType() == ParameterType.DOUBLE && field instanceof MonetaryField) {
        ((MonetaryField) field).setValue(parameter.getDoubleValue());
      }
      else {
        field.setText(parameterMap.get(code).getValue());
      }
    }
    else {
      field.setText("");
    }
  }
}

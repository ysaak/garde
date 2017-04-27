package ysaak.garde.business.repository.converter;

import ysaak.garde.business.model.parameter.ParameterType;

import javax.persistence.Converter;
import java.util.Arrays;

/**
 * Parameter type converter
 */
@Converter(autoApply=true)
public class ParameterTypeConverter extends AbstractEnumConverter<ParameterType> {

  public ParameterTypeConverter() {
    super(Arrays.asList(ParameterType.values()));
  }
}

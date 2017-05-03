package ysaak.garde.business.converter;

import ysaak.garde.business.model.parameter.Parameter;
import ysaak.garde.business.model.parameter.ParameterType;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.service.mapping.AbstractConverter;

/**
 * Parameter DTO converter
 */
public class ParameterDTOConverter extends AbstractConverter<Parameter, ParameterDTO> {

  @Override
  protected ParameterDTO convertNonNullEntity(Parameter entity) {
    ParameterDTO p = new ParameterDTO();
    p.setCode(entity.getCode());
    p.setType(lookup(ParameterType.class, ysaak.garde.data.parameter.ParameterType.class).convertEntity(entity.getType()));
    p.setValue(entity.getValue());
    return p;
  }

  @Override
  protected Parameter convertNonNullDTO(ParameterDTO dto) {
    Parameter p = new Parameter();
    p.setCode(dto.getCode());
    p.setType(lookup(ParameterType.class, ysaak.garde.data.parameter.ParameterType.class).convertDTO(dto.getType()));
    p.setValue(dto.getValue());
    return p;
  }

}

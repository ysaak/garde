package ysaak.garde.business.converter;

import ysaak.garde.business.model.Child;
import ysaak.garde.business.model.parameter.Parameter;
import ysaak.garde.business.model.parameter.ParameterType;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.service.mapping.AbstractConverter;

/**
 * Parameter DTO converter
 */
public class ChildDTOConverter extends AbstractConverter<Child, ChildDTO> {

  @Override
  protected ChildDTO convertNonNullEntity(Child child) {
    ChildDTO result = new ChildDTO();
    result.setId(child.getId());
    result.setLastName(child.getLastName());
    result.setFirstName(child.getFirstName());
    result.setBirthDate(child.getBirthDate());
    result.setSickness(child.getSickness());
    result.setComments(child.getComments());
    return result;
  }

  @Override
  protected Child convertNonNullDTO(ChildDTO child) {
    Child result = new Child();
    result.setId(child.getId());
    result.setLastName(child.getLastName());
    result.setFirstName(child.getFirstName());
    result.setBirthDate(child.getBirthDate());
    result.setSickness(child.getSickness());
    result.setComments(child.getComments());
    return result;
  }
}

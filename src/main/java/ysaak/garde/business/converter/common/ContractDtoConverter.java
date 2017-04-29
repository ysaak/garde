package ysaak.garde.business.converter.common;

import ysaak.garde.business.model.contract.Contract;
import ysaak.garde.business.model.contract.ContractStatus;
import ysaak.garde.business.model.contract.ContractType;
import ysaak.garde.data.contract.ContractDTO;
import ysaak.garde.service.mapping.AbstractConverter;

/**
 * Contract DTO mapper
 */
public class ContractDtoConverter extends AbstractConverter<Contract, ContractDTO> {

  @Override
  protected ContractDTO convertNonNullEntity(Contract contract) {
    ContractDTO result = new ContractDTO();
    result.setId(contract.getId());
    //FIXME entity.setChild(null);
    result.setType(lookup(ContractType.class, ysaak.garde.data.contract.ContractType.class).convertEntity(contract.getType()));
    result.setStatus(lookup(ContractStatus.class, ysaak.garde.data.contract.ContractStatus.class).convertEntity(contract.getStatus()));
    result.setStartDate(contract.getStartDate());
    result.setEndDate(contract.getEndDate());
    result.setWeekPerYear(contract.getWeekPerYear());
    result.setAttendancePerWeek(contract.getAttendancePerWeek());
    result.setHoursPerWeek(contract.getHoursPerWeek());
    result.setBaseHourPrice(contract.getBaseHourPrice());
    result.setIncreasedHourValue(contract.getIncreasedHourValue());
    return result;
  }

  @Override
  protected Contract convertNonNullDTO(ContractDTO contract) {
    Contract result = new Contract();
    result.setId(contract.getId());
    //FIXME entity.setChild(null);
    result.setType(lookup(ContractType.class, ysaak.garde.data.contract.ContractType.class).convertDTO(contract.getType()));
    result.setStatus(lookup(ContractStatus.class, ysaak.garde.data.contract.ContractStatus.class).convertDTO(contract.getStatus()));
    result.setStartDate(contract.getStartDate());
    result.setEndDate(contract.getEndDate());
    result.setWeekPerYear(contract.getWeekPerYear());
    result.setAttendancePerWeek(contract.getAttendancePerWeek());
    result.setHoursPerWeek(contract.getHoursPerWeek());
    result.setBaseHourPrice(contract.getBaseHourPrice());
    result.setIncreasedHourValue(contract.getIncreasedHourValue());
    return result;
  }
}

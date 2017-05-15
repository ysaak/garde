package ysaak.garde.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ysaak.garde.business.MappingConfiguration;
import ysaak.garde.business.model.Child;
import ysaak.garde.business.model.contract.Contract;
import ysaak.garde.business.model.contract.ContractStatus;
import ysaak.garde.business.model.contract.ContractType;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.data.contract.ContractDTO;

import java.time.LocalDate;

/**
 * Test class from contract service implementation
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Import(MappingConfiguration.class)
public class TestContractDtoMapper extends AbstractTestMapper {

  private final Contract entity;
  private final ContractDTO dto;

  public TestContractDtoMapper() {

    Child child = new Child();
    child.setId(2L);

    entity = new Contract();
    entity.setId(1L);
    //entity.setChild(child);
    entity.setType(ContractType.FULL);
    entity.setStatus(ContractStatus.ACTIVE);
    entity.setStartDate(LocalDate.of(2017, 1, 1));
    entity.setEndDate(LocalDate.of(2017, 2, 1));
    entity.setWeekPerYear(52);
    entity.setAttendancePerWeek(4);
    entity.setHoursPerWeek(20);
    entity.setBaseHourPrice(10.);
    entity.setIncreasedHourValue(30.);

    // DTO creation
    ChildDTO childDto = new ChildDTO();
    child.setId(2L);

    dto = new ContractDTO();
    dto.setId(1L);
    //dto.setChild(childDto);
    dto.setType(ysaak.garde.data.contract.ContractType.FULL);
    dto.setStatus(ysaak.garde.data.contract.ContractStatus.ACTIVE);
    dto.setStartDate(LocalDate.of(2017, 1, 1));
    dto.setEndDate(LocalDate.of(2017, 2, 1));
    dto.setWeekPerYear(52);
    dto.setAttendancePerWeek(4);
    dto.setHoursPerWeek(20);
    dto.setBaseHourPrice(10.);
    dto.setIncreasedHourValue(30.);
  }

  @Test
  public void testContractConversion() {
    testEntityConversion(entity, Contract.class, dto, ContractDTO.class);
  }

  @Test
  public void testContractDTOConversion() {
    testDTOConversion(dto, ContractDTO.class, entity, Contract.class);
  }
}

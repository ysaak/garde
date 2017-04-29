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
public class TestChildDtoMapper extends AbstractTestMapper {

  private final Child entity;
  private final ChildDTO dto;

  public TestChildDtoMapper() {

    entity = new Child();
    entity.setId(2L);
    entity.setLastName("LAST NAME");
    entity.setFirstName("FIRST NAME");
    entity.setBirthDate(LocalDate.of(2017, 1, 1));
    entity.setComments("CLine1\nCLine2\nCLine3");
    entity.setSickness("SLine1\nSLine2\nSLine3");

    // DTO creation
    dto = new ChildDTO();
    dto.setId(2L);
    dto.setLastName("LAST NAME");
    dto.setFirstName("FIRST NAME");
    dto.setBirthDate(LocalDate.of(2017, 1, 1));
    dto.setComments("CLine1\nCLine2\nCLine3");
    dto.setSickness("SLine1\nSLine2\nSLine3");
  }

  @Test
  public void testContractConversion() {
    testEntityConversion(entity, Child.class, dto, ChildDTO.class);
  }

  @Test
  public void testContractDTOConversion() {
    testDTOConversion(dto, ChildDTO.class, entity, Child.class);
  }
}

package ysaak.garde.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ysaak.garde.business.MappingConfiguration;
import ysaak.garde.business.model.parameter.Parameter;
import ysaak.garde.business.model.parameter.ParameterType;
import ysaak.garde.data.parameter.ParameterDTO;

/**
 * Test class from contract service implementation
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Import(MappingConfiguration.class)
public class TestParameterDtoMapper extends AbstractTestMapper {

  private final Parameter entity;
  private final ParameterDTO dto;

  public TestParameterDtoMapper() {
    entity = new Parameter();
    entity.setId(1L);
    entity.setCode("CODE");
    entity.setType(ParameterType.STRING);
    entity.setValue("VALUE");

    dto = new ParameterDTO();
    dto.setId(1L);
    dto.setCode("CODE");
    dto.setType(ysaak.garde.data.parameter.ParameterType.STRING);
    dto.setValue("VALUE");
  }

  @Test
  public void testParameterConversion() {
    testEntityConversion(entity, Parameter.class, dto, ParameterDTO.class);
  }

  @Test
  public void testParameterDTOConversion() {
    testDTOConversion(dto, ParameterDTO.class, entity, Parameter.class);
  }
}

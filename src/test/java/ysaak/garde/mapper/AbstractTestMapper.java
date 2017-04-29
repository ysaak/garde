package ysaak.garde.mapper;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.service.mapping.Converter;
import ysaak.garde.service.mapping.MappingEngine;

/**
 * Abstract class from mapper test
 */
abstract class AbstractTestMapper {

  @Autowired
  private MappingEngine mappingEngine;

  protected <Entity, DTO> void testEntityConversion(Entity entity, Class<Entity> entityClass, DTO expectedResult, Class<DTO> dtoClass) {
    Converter<Entity, DTO> converter = mappingEngine.lookup(entityClass, dtoClass);
    Assert.assertNotNull(converter);

    DTO result = converter.convertEntity(entity);

    Assert.assertNotNull(result);
    Assert.assertEquals(expectedResult, result);
  }

  protected <Entity, DTO> void testDTOConversion(DTO dto, Class<DTO> dtoClass, Entity expectedResult, Class<Entity> entityClass) {
    Converter<Entity, DTO> converter = mappingEngine.lookup(entityClass, dtoClass);
    Assert.assertNotNull(converter);

    Entity result = converter.convertDTO(dto);

    Assert.assertNotNull(result);
    Assert.assertEquals(expectedResult, result);
  }
}

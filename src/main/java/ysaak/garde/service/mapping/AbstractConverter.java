package ysaak.garde.service.mapping;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Abstract converter
 */
public abstract class AbstractConverter<Entity, DTO> implements Converter<Entity, DTO> {

  /**
   * Mapping engine
   */
  private MappingEngine engine;

  private final Class<Entity> entityClass;
  private final Class<DTO> dtoClass;

  @SuppressWarnings("unchecked")
  public AbstractConverter() {
    this.engine = null;

    // Find generic classes
    Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
    this.entityClass = (Class<Entity>) types[0];
    this.dtoClass = (Class<DTO>) types[1];
  }

  /**
   * Defines the mapping engine
   * @param engine Mapping engine
   */
  public void setEngine(MappingEngine engine) {
    Preconditions.checkNotNull(engine, "engine");
    this.engine = engine;
  }

  @Override
  public DTO convertEntity(Entity entity) {
    return entity != null ? convertNonNullEntity(entity) : null;
  }

  /**
   * Convert a non null entity to a DTO
   * @param entity Non null entity to convert
   * @return DTO
   * @throws ConversionException if a error occurs during the conversion
   */
  protected DTO convertNonNullEntity(Entity entity) {
    throw new UnsupportedConversionException(entityClass, dtoClass);
  }

  @Override
  public Entity convertDTO(DTO dto) {
    return dto != null ? convertNonNullDTO(dto) : null;
  }

  /**
   * Convert a non null DTO to an entity
   * @param dto Non null DTO to convert
   * @return Entity
   * @throws ConversionException if a error occurs during the conversion
   */
  protected Entity convertNonNullDTO(DTO dto) {
    throw new UnsupportedConversionException(dtoClass, entityClass);
  }

  /**
   * Look for a converter for the specified types
   * @param entityClass Entity class
   * @param dtoClass DTO class
   * @return Converter
   */
  protected <F, T> Converter<F,T> lookup(Class<F> entityClass, Class<T> dtoClass) {
    return engine.lookup(entityClass, dtoClass);
  }
}

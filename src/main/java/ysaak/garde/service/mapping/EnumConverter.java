package ysaak.garde.service.mapping;

/**
 * Simple enum converter which convert using string value. Also work from String to Enum
 */
public abstract class EnumConverter<Entity, DTO> implements Converter<Entity, DTO> {

  private final Class<Entity> entityClass;
  private final Class<DTO> dtoClass;

  public EnumConverter(Class<Entity> entityClass, Class<DTO> dtoClass) {
    this.entityClass = entityClass;
    this.dtoClass = dtoClass;
  }

  @Override
  public DTO convertEntity(Entity entity) {
    return (entity != null) ? convert(entity, entityClass, dtoClass) : null;
  }

  @Override
  public Entity convertDTO(DTO dto) {
    return (dto != null) ? convert(dto, dtoClass, entityClass) : null;
  }

  /**
   * Convert an enum to another one through their string value
   * @param source Source value
   * @param sourceClass Source class
   * @param destinationClass Destination class
   * @return Destination value
   * @throws ConversionException if no value is found in destination enum
   */
  @SuppressWarnings("unchecked")
  private <S,D> D convert(S source, Class<S> sourceClass, Class<D> destinationClass) {
    // Convert source to string
    final String sourceString = source.toString();

    D result = null;

    if (String.class.isAssignableFrom(destinationClass)) {
      // Destination class is String, just return source string
      result = (D) sourceString;
    }
    else {
      // Search from enum values a matching string
      final D[] values = destinationClass.getEnumConstants();

      if (values != null) {
        // Search for string in enum values
        for (D value : values) {
          if (sourceString.equals(value.toString())) {
            result = value;
            break;
          }
        }
      }

      if (result == null) {
        // No result found, throw exception
        throw new ConversionException("Error while converting enum from " + sourceClass.toGenericString() + " to " + destinationClass.toGenericString());
      }
    }

    return result;
  }
}

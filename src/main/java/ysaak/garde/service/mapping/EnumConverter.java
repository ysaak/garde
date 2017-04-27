package ysaak.garde.service.mapping;

/**
 * Simple enum converter which convert using string value. Also work from String to Enum
 */
public abstract class EnumConverter<Entity, DTO> extends AbstractConverter<Entity, DTO> {

  @Override
  protected DTO convertNonNullEntity(Entity entity) {
    return convert(entity, entityClass, dtoClass);
  }

  @Override
  protected Entity convertNonNullDTO(DTO dto) {
    return convert(dto, dtoClass, entityClass);
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
        throw new ConversionException("Error while converting enum from " + entityClass.toGenericString() + " to " + dtoClass.toGenericString());
      }
    }

    return result;
  }
}

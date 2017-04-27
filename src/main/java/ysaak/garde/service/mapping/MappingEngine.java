package ysaak.garde.service.mapping;

import com.google.common.base.Preconditions;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.MutableClassToInstanceMap;
import com.google.common.collect.Table;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Engine for the mapping
 */
public class MappingEngine {
  private static final Logger LOGGER = LoggerFactory.getLogger(MappingEngine.class);

  private final Table<Class<?>, Class<?>, Class<? extends Converter<?, ?>>> converterStore;
  private final ClassToInstanceMap<Object> converterInstances;

  private final Table<Class<?>, Class<?>, Converter<?, ?>> enumConverterInstances;

  public MappingEngine() {
    this.converterStore = HashBasedTable.create();
    this.converterInstances = MutableClassToInstanceMap.create();
    this.enumConverterInstances = HashBasedTable.create();
  }

  /**
   * Register a new converter
   * @param converter Converter
   */
  @SuppressWarnings("unchecked")
  public <Entity, DTO> void register(Class<Converter<?, ?>> converter) {
    Preconditions.checkNotNull(converter, "Converter is null");

    // Find generic classes
    final TypeToken<? extends Converter<?, ?>> convertToken = TypeToken.of(converter);
    Class<Entity> fromClass = (Class<Entity>) convertToken.resolveType(Converter.class.getTypeParameters()[0]).getRawType();
    Class<DTO> toClass = (Class<DTO>) convertToken.resolveType(Converter.class.getTypeParameters()[1]).getRawType();

    LOGGER.debug("Registering converter from " + fromClass.toGenericString() + " to " + toClass.toGenericString());

    // Store the converter
    converterStore.put(fromClass, toClass, converter);
  }

  /**
   * Look for a converter for the two specified classes
   * @param entityClass Entity class
   * @param dtoClass DTO class
   * @return Converter
   */
  @SuppressWarnings("unchecked")
  public <Entity, DTO> Converter<Entity, DTO> lookup(Class<Entity> entityClass, Class<DTO> dtoClass) {
    Preconditions.checkNotNull(entityClass, "entityClass");
    Preconditions.checkNotNull(dtoClass, "dtoClass");

    // Search for converter class
    final Class<? extends Converter<?,?>> converterClass = converterStore.get(entityClass, dtoClass);

    Converter<Entity, DTO> converterInstance = null;

    if (converterClass == null && entityClass.isEnum() && dtoClass.isEnum()) {
      // Still no converter found and the two classes are enums
      converterInstance = loadEnumConverter(entityClass, dtoClass);
    }
    else {
      // Load converter instance
      if (converterClass != null) {
        converterInstance = getOrCreateInstance(converterClass);
      }

      if (converterInstance == null) {
        throw new ConversionException("No converter found for " + entityClass.toGenericString() + " to " + dtoClass.toGenericString());
      }
    }

    return converterInstance;
  }

  /**
   * Load an enum converter based on the simple converter
   * @param entityClass Entity class
   * @param dtoClass DTO class
   * @return Enum converter
   */
  @SuppressWarnings("unchecked")
  private <Entity, DTO> Converter<Entity, DTO> loadEnumConverter(Class<Entity> entityClass, Class<DTO> dtoClass) {
    Converter<Entity, DTO> converter = (Converter<Entity, DTO>) enumConverterInstances.get(entityClass, dtoClass);

    if (converter == null) {
      converter = new EnumConverter<Entity, DTO>() {};
      enumConverterInstances.put(entityClass, dtoClass, converter);
    }

    return converter;
  }

  /**
   * Get or create a new instance of a converter
   * @param converter Converter class
   * @return Instance of the requested converter
   */
  @SuppressWarnings("unchecked")
  private <M> M getOrCreateInstance(final Class<?> converter) {

    Object instance = converterInstances.get(converter);

    if (instance == null) {
      try {
        instance = converter.newInstance();
        if (converter.isInstance(AbstractConverter.class)) {
          ((AbstractConverter) instance).setEngine(this);
        }

        converterInstances.put(converter, instance);
      }
      catch (Exception e) {
        throw new ConversionException("unable to create instance of mappingEngine", e);
      }
    }

    return (M) instance;
  }
}

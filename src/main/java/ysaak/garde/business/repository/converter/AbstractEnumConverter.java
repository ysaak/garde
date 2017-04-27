package ysaak.garde.business.repository.converter;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract enum converter
 */
abstract class AbstractEnumConverter<T extends Enum<T>>  implements AttributeConverter<T, String> {
  private final BiMap<T, String> map;
  private final T defaultValue;

  AbstractEnumConverter(List<T> enumValues) {
    this(enumValues, nameToString(enumValues));
  }

  AbstractEnumConverter(List<T> enumValues, List<String> stringValues) {
    this(enumValues, stringValues, null);
  }

  AbstractEnumConverter(List<T> enumValues, List<String> stringValues, T defaultValue) {
    Preconditions.checkNotNull(enumValues);
    Preconditions.checkNotNull(stringValues);
    Preconditions.checkArgument(enumValues.size() == stringValues.size(), "Lists size differs. Enums: %d / String: %d ", enumValues.size(), stringValues.size());

    int size = enumValues.size();

    this.map = HashBiMap.create(size);

    for (int i=0; i<size; i++) {
      this.map.put(enumValues.get(i), stringValues.get(i));
    }

    this.defaultValue = defaultValue;
  }

  @Override
  public String convertToDatabaseColumn(T attribute) {
    return this.map.get(attribute);
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    return this.map.inverse().getOrDefault(dbData, defaultValue);
  }

  private static <E> List<String> nameToString(List<E> enums) {
    return enums.stream().map(e -> e.toString()).collect(Collectors.toList());
  }
}

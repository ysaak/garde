package ysaak.hera.nexus.business.repository.converter;

import java.time.LocalTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalTimeConverter implements AttributeConverter<LocalTime, String> {

  @Override
  public String convertToDatabaseColumn(LocalTime attribute) {
    if (attribute != null) {
      return attribute.toString();
    }
    return null;
  }

  @Override
  public LocalTime convertToEntityAttribute(String dbData) {
    if (dbData != null) {
      return LocalTime.parse(dbData);
    }
    return null;
  }

}

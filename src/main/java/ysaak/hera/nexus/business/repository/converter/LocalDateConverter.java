package ysaak.hera.nexus.business.repository.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply=true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

  @Override
  public Date convertToDatabaseColumn(LocalDate attribute) {
    if (attribute != null) {
      return Date.valueOf(attribute);
    }
    return null;
  }

  @Override
  public LocalDate convertToEntityAttribute(Date dbData) {
    if (dbData != null) {
      return dbData.toLocalDate();
    }
    return null;
  }
}

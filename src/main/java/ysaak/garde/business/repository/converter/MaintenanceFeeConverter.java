package ysaak.garde.business.repository.converter;

import ysaak.garde.business.model.attendance.MaintenanceFee;

import javax.persistence.Converter;
import java.util.Arrays;

/**
 * Maintenance fee converter
 */
@Converter(autoApply=true)
public class MaintenanceFeeConverter extends AbstractEnumConverter<MaintenanceFee> {

  public MaintenanceFeeConverter() {
    super(
            Arrays.asList(MaintenanceFee.YES, MaintenanceFee.NO),
            Arrays.asList("Y", "N")
    );
  }
}

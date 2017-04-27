package ysaak.garde.business.repository.converter;

import ysaak.garde.business.model.attendance.MealFee;

import javax.persistence.Converter;
import java.util.Arrays;

/**
 * Meal fee converter
 */
@Converter(autoApply=true)
public class MealFeeConverter extends AbstractEnumConverter<MealFee> {

  public MealFeeConverter() {
    super(
            Arrays.asList(MealFee.FULL, MealFee.PARTIAL, MealFee.NONE),
            Arrays.asList("FUL", "PAR", "NON")
    );
  }
}

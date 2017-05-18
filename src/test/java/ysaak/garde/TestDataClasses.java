package ysaak.garde;

import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Test;

/**
 * Test class for DTO and models classes
 */
public class TestDataClasses {
  private static final String MODEL_PACKAGE = "ysaak.garde.business.model";
  private static final String DTO_PACKAGE = "ysaak.garde.data";


  @Test
  public void testModelsStructureAndBehavior() {
    Validator validator = ValidatorBuilder.create()
            .with(new GetterMustExistRule())
            .with(new SetterMustExistRule())
            .with(new SetterTester())
            .with(new GetterTester())
            .build();

    validator.validateRecursively(MODEL_PACKAGE, new FilterPackageInfo());
  }

  @Test
  public void testDTOsStructureAndBehavior() {
    Validator validator = ValidatorBuilder.create()
            .with(new GetterMustExistRule())
            .with(new SetterMustExistRule())
            .with(new SetterTester())
            .with(new GetterTester())
            .build();

    validator.validateRecursively(DTO_PACKAGE, new FilterPackageInfo());
  }
}

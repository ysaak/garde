package ysaak.garde.business.repository.converter;

import ysaak.garde.business.model.contract.ContractType;

import javax.persistence.Converter;
import java.util.Arrays;

/**
 * Contract type converter
 */
@Converter(autoApply=true)
public class ContractTypeConverter extends AbstractEnumConverter<ContractType> {

  public ContractTypeConverter() {
    super(
            Arrays.asList(ContractType.FULL, ContractType.PARTIAL, ContractType.OCCASIONAL),
            Arrays.asList("FUL", "PAR", "OCC")
    );
  }
}

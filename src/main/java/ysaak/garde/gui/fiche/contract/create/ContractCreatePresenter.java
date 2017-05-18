package ysaak.garde.gui.fiche.contract.create;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.contract.ContractService;
import ysaak.garde.business.service.parameter.ParameterService;
import ysaak.garde.data.contract.ContractDTO;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.annotation.Module;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.gui.common.presenter.AbstractPresenter;

import java.util.List;

@Module("CONTRACT-CREATE")
public class ContractCreatePresenter extends AbstractPresenter<ContractDTO, ContractCreateView> {

  @Autowired
  private ParameterService parameterService;

  @Autowired
  private ContractService contractService;

  private List<ParameterDTO> parameters = null;

  public ContractCreatePresenter() {
    super(ButtonBarType.EDIT);
  }

  @Override
  protected ContractDTO loadData(Context context) throws Exception {
    if (parameters == null) {
      parameters = parameterService.listAll();
    }

    return null;
  }

  @Override
  protected void setExtraData() {
    view.setParameters(parameters);
  }

  @Override
  protected void updateData(ContractDTO data) throws Exception {

    contractService.create(data);

  }
}

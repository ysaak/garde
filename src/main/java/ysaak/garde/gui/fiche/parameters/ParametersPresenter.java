package ysaak.garde.gui.fiche.parameters;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.parameter.ParameterService;
import ysaak.garde.data.parameter.ParameterDTO;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.annotation.Fiche;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.gui.common.presenter.AbstractPresenter;

import java.util.List;

@Fiche("PARAMETERS")
public class ParametersPresenter extends AbstractPresenter<List<ParameterDTO>, ParametersView> {

  @Autowired
  private ParameterService parameterService;

  public ParametersPresenter() {
    super(ButtonBarType.EDIT);
  }

  @Override
  protected List<ParameterDTO> loadData(Context context) throws Exception {
    return parameterService.listAll();
  }

  @Override
  protected void updateData(List<ParameterDTO> data) throws Exception {
    parameterService.save(data);
  }
}

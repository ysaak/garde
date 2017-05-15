package ysaak.garde.gui.fiche.root;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.annotation.Fiche;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.gui.common.presenter.AbstractPresenter;
import ysaak.garde.gui.events.leftpanel.LeftPanelUpdateEvent;

import java.util.Arrays;
import java.util.List;

@Fiche(value="ROOT", root=true)
public class RootPresenter extends AbstractPresenter<List<ChildDTO>, RootView> {

  private static final List<String> VIEW_CODE_EXCEPTIONS = Arrays.asList("PARAMETERS", "CONTRACT-CREATE");

  @Autowired
  private ChildService childService;

  public RootPresenter() {
    super(ButtonBarType.NONE);
  }

  @Override
  protected List<ChildDTO> loadData(Context context) throws Exception {
    return childService.listAll();
  }

  @Override
  protected void updateData(List<ChildDTO> data) throws Exception {
    // Not used
  }

  @Override
  protected void fireOpenFormRequest(String viewCode, Context context) {
    if (!VIEW_CODE_EXCEPTIONS.contains(viewCode)) {
      eventFacade.post(new LeftPanelUpdateEvent(context.getLongId()));
    }

    super.fireOpenFormRequest(viewCode, context);
  }
}

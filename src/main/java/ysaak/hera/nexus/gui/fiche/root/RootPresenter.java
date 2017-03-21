package ysaak.hera.nexus.gui.fiche.root;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.child.ChildService;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBarType;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;
import ysaak.hera.nexus.gui.events.leftpanel.LeftPanelUpdateEvent;

import java.util.Arrays;
import java.util.List;

@Fiche(value="ROOT", root=true)
public class RootPresenter extends AbstractPresenter<List<Child>, RootView> {

  private static final List<String> VIEW_CODE_EXCEPTIONS = Arrays.asList("", "");

  @Autowired
  private ChildService childService;

  public RootPresenter() {
    super(ButtonBarType.NONE);
  }

  @Override
  protected List<Child> loadData(Context context) throws Exception {
    return childService.listAll();
  }

  @Override
  protected void updateData(List<Child> data) throws Exception {
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

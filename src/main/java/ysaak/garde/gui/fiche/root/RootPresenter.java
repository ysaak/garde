package ysaak.garde.gui.fiche.root;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.annotation.Module;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.gui.common.presenter.AbstractPresenter;
import ysaak.garde.gui.events.ChildSelectionEvent;
import ysaak.garde.service.EventFacade;

import java.util.Arrays;
import java.util.List;

@Module(value="ROOT", root = true, reloadOnRedisplay = true)
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
      EventFacade.post(new ChildSelectionEvent(context.getLongId()));
    }

    super.fireOpenFormRequest(viewCode, context);
  }

  @Override
  public boolean reloadOnDisplay() {
    return true;
  }
}

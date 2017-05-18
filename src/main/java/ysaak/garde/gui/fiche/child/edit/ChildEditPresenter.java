package ysaak.garde.gui.fiche.child.edit;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.annotation.Module;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.gui.common.presenter.AbstractPresenter;
import ysaak.garde.gui.events.ChildSelectionEvent;
import ysaak.garde.service.EventFacade;

@Module("CHILD-EDIT")
public class ChildEditPresenter extends AbstractPresenter<ChildDTO, ChildEditView> {

  @Autowired
  private ChildService childService;

  public ChildEditPresenter() {
    super(ButtonBarType.EDIT);
  }

  @Override
  protected ChildDTO loadData(Context context) throws Exception {

    ChildDTO data = null;

    if (context.getChildId() != null) {
      data = childService.get(context.getChildId());
    }

    return data;
  }

  @Override
  protected void updateData(ChildDTO data) throws Exception {
    final ChildDTO child = childService.save(data);

    if (child.getId() != null) {
      EventFacade.post(new ChildSelectionEvent(child.getId()));
    }
  }
}

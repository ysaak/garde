package ysaak.garde.gui.fiche.child.edit;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.data.Child;
import ysaak.garde.gui.common.annotation.Fiche;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.presenter.AbstractPresenter;

@Fiche("CHILD-EDIT")
public class ChildEditPresenter extends AbstractPresenter<Child, ChildEditView> {

  @Autowired
  private ChildService childService;

  public ChildEditPresenter() {
    super(ButtonBarType.EDIT);
  }

  @Override
  protected Child loadData(Context context) throws Exception {

    Child data = null;

    if (context.getChildId() != null) {
      data = childService.get(context.getChildId());
    }

    return data;
  }

  @Override
  protected void updateData(Child data) throws Exception {
    childService.save(data);
  }
}

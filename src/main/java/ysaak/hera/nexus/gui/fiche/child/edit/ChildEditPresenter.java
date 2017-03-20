package ysaak.hera.nexus.gui.fiche.child.edit;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.child.ChildService;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;

@Fiche("CHILD-EDIT")
public class ChildEditPresenter extends AbstractPresenter<Child, ChildEditView> {

  @Autowired
  private ChildService childService;

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

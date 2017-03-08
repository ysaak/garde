package ysaak.hera.nexus.gui.fiche.child.root;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.child.ChildService;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;

@Fiche("CHILD-ROOT")
public class ChildRootPresenter extends AbstractPresenter<Long, ChildRootView> {

  @Autowired
  private ChildService childService;

  @Override
  protected Long loadData(Context context) throws Exception {
    return context.getLongId();
  }

  @Override
  protected void updateData(Long data) throws Exception {
    // Not used
  }
}

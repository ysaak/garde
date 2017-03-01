package ysaak.hera.nexus.gui.fiche.root;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.child.ChildService;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.presenter.AbstractFormPresenter;

import java.util.List;

@Fiche(value="ROOT", root=true)
public class RootPresenter extends AbstractFormPresenter<List<Child>, RootView> {

  @Autowired
  private ChildService childService;

  @Override
  protected RootView initView() {
    return viewLoader.loadView(RootView.class);
  }

  @Override
  protected List<Child> loadData(Context context) throws Exception {
    return childService.listAll();
  }

  @Override
  protected void updataData(List<Child> data) throws Exception {
    // Not used
  }
}

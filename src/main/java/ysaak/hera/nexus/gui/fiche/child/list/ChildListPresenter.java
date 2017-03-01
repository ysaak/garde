package ysaak.hera.nexus.gui.fiche.child.list;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.child.ChildService;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.presenter.AbstractFichePresenter;

import java.util.List;

@Fiche(value="CHILD-LIST", root=true)
public class ChildListPresenter extends AbstractFichePresenter<List<Child>, ChildListView> {

  @Autowired
  private ChildService childService;
  
  public ChildListPresenter() {
    super(null);
  }

  @Override
  protected ChildListView initView() {
    return new ChildListView();
  }

  @Override
  protected List<Child> loadData(Context context) throws Exception {
    
    System.err.println("WWW > " + childService.hasBirthDayNearby());
    
    return childService.listAll();
  }

  @Override
  protected void updataData(List<Child> data) throws Exception {
    // Not used
  }
}

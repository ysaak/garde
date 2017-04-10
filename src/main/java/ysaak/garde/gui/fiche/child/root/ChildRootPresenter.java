package ysaak.garde.gui.fiche.child.root;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.gui.common.annotation.Fiche;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.gui.common.presenter.AbstractPresenter;

@Fiche("CHILD-ROOT")
public class ChildRootPresenter extends AbstractPresenter<Long, ChildRootView> {

  @Autowired
  private ChildService childService;

  public ChildRootPresenter() {
    super(ButtonBarType.NONE);
  }

  @Override
  protected Long loadData(Context context) throws Exception {
    return context.getLongId();
  }

  @Override
  protected void updateData(Long data) throws Exception {
    // Not used
  }
}

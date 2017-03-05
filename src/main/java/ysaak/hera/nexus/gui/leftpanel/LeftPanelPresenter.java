package ysaak.hera.nexus.gui.leftpanel;

import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.business.service.child.ChildService;
import ysaak.hera.nexus.data.Child;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.ContextBuilder;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;
import ysaak.hera.nexus.gui.events.leftpanel.LeftPanelUpdateEvent;

/**
 * Left panel presenter
 */
public class LeftPanelPresenter extends AbstractPresenter<Child, ChildLeftPanel> {

  @Autowired
  private ChildService childService;

  @Subscribe
  public void onLeftPanelUpdateEvent(LeftPanelUpdateEvent event) {
    if (event != null && event.getChildId() != null) {
      startLoadData(ContextBuilder.get().withId(event.getChildId()).build());
    }
  }

  @Override
  protected Child loadData(Context context) throws Exception {
    if (context != null && context.getLongId() != null) {
      return childService.get(context.getLongId());
    }

    return null;
  }

  @Override
  protected void setData(Child child) {
    if (child == null) {
      getView().setVisible(false);
    }
    else {
      super.setData(child);
      getView().setVisible(true);
    }
  }

  @Override
  protected void updataData(Child child) throws Exception {
    // Nothing to save
  }
}

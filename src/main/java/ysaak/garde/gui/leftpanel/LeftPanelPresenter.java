package ysaak.garde.gui.leftpanel;

import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.business.service.child.ChildService;
import ysaak.garde.data.ChildDTO;
import ysaak.garde.gui.common.Dialogs;
import ysaak.garde.gui.events.ChildSelectionEvent;
import ysaak.garde.gui.events.view.CloseFormEvent;
import ysaak.garde.service.EventFacade;
import ysaak.garde.service.task.GuiTask;
import ysaak.garde.service.task.TaskFacade;
import ysaak.garde.service.translation.I18n;

/**
 * Left panel presenter
 */
public class LeftPanelPresenter {

  @Autowired
  private TaskFacade taskFacade;

  @Autowired
  private ChildService childService;

  private final LeftPanelView view;


  public LeftPanelPresenter() {
    view = new LeftPanelView();

    view.setOnTerminateAction(evt -> {
      CloseFormEvent closeEvt = new CloseFormEvent();
      closeEvt.setCloseWholeContext(true);
      EventFacade.post(closeEvt);

      view.setVisible(false);
    });

    view.setVisible(false);

    EventFacade.register(this);
  }


  @Subscribe
  public void onChildUpdateEvent(ChildSelectionEvent event) {
    if (event != null && event.getChildId() != null) {
      taskFacade.submit(new ChildLoaderTask(event.getChildId()));
    }
  }

  private void setData(ChildDTO child) {
    if (child == null) {
      view.setVisible(false);
    }
    else {
      view.setChildData(child);
      view.setVisible(true);
    }
  }

  public LeftPanelView getView() {
    return view;
  }

  private class ChildLoaderTask extends GuiTask<ChildDTO> {

    private final long childId;

    private ChildLoaderTask(long childId) {
      this.childId = childId;
    }

    @Override
    public ChildDTO call() throws Exception {
      return childService.get(childId);
    }

    @Override
    public void onSucceeded(ChildDTO result) {
      setData(result);
    }

    @Override
    public void onFailed(Throwable error) {
      Dialogs.error(I18n.get("left-panel.error.load"));
    }
  }
}

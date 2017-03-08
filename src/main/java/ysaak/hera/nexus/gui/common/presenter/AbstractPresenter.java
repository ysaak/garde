package ysaak.hera.nexus.gui.common.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.ViewLoader;
import ysaak.hera.nexus.gui.common.actions.ActionType;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBarAction;
import ysaak.hera.nexus.gui.common.components.ModulePane;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;
import ysaak.hera.nexus.gui.common.view.ViewListener;
import ysaak.hera.nexus.gui.events.view.CloseFormEvent;
import ysaak.hera.nexus.gui.events.view.OpenFormEvent;
import ysaak.hera.nexus.service.event.EventFacade;
import ysaak.hera.nexus.service.task.GuiTask;
import ysaak.hera.nexus.service.task.TaskFacade;
import ysaak.hera.nexus.service.task.TaskType;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractPresenter<DATA, VIEW extends AbstractFormView<DATA>> implements Presenter {

  private class DataLoaderTask extends GuiTask<DATA> {
    private final Context context;

    private DataLoaderTask(final Context context) {
      super(TaskType.LOAD, pane);
      this.context = context;
    }

    @Override
    public DATA call() throws Exception {
      return loadData(context);
    }

    @Override
    public void onSucceeded(DATA result) {
      setData(result);
    }

    @Override
    public void onFailed(Throwable error) {
      showError(error);
    }
  }

  private class DataUpdaterTask extends GuiTask<DATA> {

    private DataUpdaterTask() {
      super(TaskType.UPDATE, pane);
    }

    @Override
    public DATA call() throws Exception {
      updateData(getData());
      return null;
    }

    @Override
    public void onSucceeded(DATA result) {
      closeView();
    }

    @Override
    public void onFailed(Throwable error) {
      showError(error);
    }
  }

  @Autowired
  protected EventFacade eventFacade;

  @Autowired
  protected ViewLoader viewLoader;

  @Autowired
  protected TaskFacade taskFacade;

  protected ModulePane pane;

  protected VIEW view;

  protected Context currentContext = null;

  private final Class<VIEW> viewClass;

  @SuppressWarnings("unchecked")
  public AbstractPresenter() {
    this.viewClass = (Class<VIEW>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
  }
  
  @Override
  public void init() {
    eventFacade.register(this);

    pane = new ModulePane();
    this.view = initView();

    // Store the default action listener
    this.view.setActionListener(this::onActionEvent);
    this.pane.setCenter(this.view.getView());


    pane.setTitle(view.getTitle());
    pane.setToolbarComponents(view.getToolbarComponents());


    final ButtonBar buttonBar = view.getButtonBar();
    pane.setButtonBar(buttonBar);

    if (buttonBar != null) {

      buttonBar.hasChangedProperty().bind(this.view.hasChangedProperty());
      buttonBar.isValidProperty().bind(this.view.isValidProperty());

      buttonBar.setListener(action -> {
        if (action == ButtonBarAction.FINISH || action == ButtonBarAction.CANCEL) {
          closeView();
        }
        else if (action == ButtonBarAction.SAVE) {
          startUpdateData();
        }
      });
    }

    view.setListener(new ViewListener() {

      @Override
      public void openForm(String viewCode, Context context) {
        fireOpenFormRequest(viewCode, context);
      }

      @Override
      public ViewLoader getViewLoader() {
        return viewLoader;
      }
    });
  }

  protected VIEW initView() {
    return viewLoader.loadView(viewClass);
  }

  @Override
  public void startLoadData(final Context context) {
    currentContext = context;
    taskFacade.submit(new DataLoaderTask(context));
  }
  
  private void startUpdateData() {
    taskFacade.submit(new DataUpdaterTask());
  }
  
  protected abstract DATA loadData(Context context) throws Exception;
  
  protected abstract void updateData(DATA data) throws Exception;
  
  private void showError(Throwable error) {
    //FIXME correctly trace and show error
    error.printStackTrace();
  }
  
  private void closeView() {
    eventFacade.unregister(this);
    eventFacade.post(new CloseFormEvent(this));
  }
  
  protected void fireOpenFormRequest(String viewCode, Context context) {
    eventFacade.post(new OpenFormEvent(viewCode, context));
  }

  protected void onActionEvent(ActionType action, DATA data) {
    // Override to use
  }

  @Override
  public ModulePane getView() {
    return pane;
  }

  protected DATA getData() {
    return view.getData();
  }

  protected void setData(DATA data) {
    view.setData(data);
  }
}

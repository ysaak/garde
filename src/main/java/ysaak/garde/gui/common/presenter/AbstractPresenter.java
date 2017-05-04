package ysaak.garde.gui.common.presenter;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ysaak.garde.gui.MainViewController;
import ysaak.garde.gui.common.ViewLoader;
import ysaak.garde.gui.common.actions.ActionType;
import ysaak.garde.gui.common.buttonbar.ButtonBar;
import ysaak.garde.gui.common.buttonbar.ButtonBarAction;
import ysaak.garde.gui.common.buttonbar.ButtonBarFactory;
import ysaak.garde.gui.common.buttonbar.ButtonBarType;
import ysaak.garde.gui.common.components.ModulePane;
import ysaak.garde.gui.common.view.AbstractFormView;
import ysaak.garde.gui.common.view.ViewListener;
import ysaak.garde.gui.events.view.CloseFormEvent;
import ysaak.garde.gui.events.view.OpenFormEvent;
import ysaak.garde.service.event.EventFacade;
import ysaak.garde.service.task.GuiTask;
import ysaak.garde.service.task.TaskType;
import ysaak.garde.gui.common.Context;
import ysaak.garde.service.task.TaskFacade;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractPresenter<DATA, VIEW extends AbstractFormView<DATA>> implements Presenter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractPresenter.class);

  private class DataLoaderTask extends GuiTask<DATA> {
    private final Context context;

    private DataLoaderTask(final Context context) {
      super(TaskType.LOAD, rootPane);
      this.context = context;
    }

    @Override
    public DATA call() throws Exception {
      return loadData(context);
    }

    @Override
    public void onSucceeded(DATA result) {
      setExtraData();
      setData(result);
    }

    @Override
    public void onFailed(Throwable error) {
      showError(error);
    }
  }

  private class DataUpdaterTask extends GuiTask<DATA> {

    private DataUpdaterTask() {
      super(TaskType.UPDATE, rootPane);
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

  protected ModulePane rootPane;

  protected VIEW view;

  protected Context currentContext = null;

  private final ButtonBarType buttonBarType;

  private final Class<VIEW> viewClass;

  public AbstractPresenter() {
    this(ButtonBarType.DEFAULT);
  }

  @SuppressWarnings("unchecked")
  public AbstractPresenter(ButtonBarType buttonBarType) {
    Preconditions.checkNotNull(buttonBarType, "ButtonBarType cannot be null");

    this.buttonBarType = buttonBarType;

    this.viewClass = (Class<VIEW>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
  }
  
  @Override
  public void init() {
    eventFacade.register(this);

    rootPane = new ModulePane();
    this.view = initView();

    // Store the default action listener
    this.view.setActionListener(this::onActionEvent);
    this.rootPane.setCenter(this.view.getView());

    rootPane.titleProperty().bind(view.titleProperty());
    rootPane.setToolbarComponents(view.getToolbarComponents());

    // Build button bar
    final ButtonBar buttonBar = ButtonBarFactory.get(buttonBarType); //view.getButtonBar();
    rootPane.setButtonBar(buttonBar);

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

  @Override
  public void startReloadData() {
    startLoadData(currentContext);
  }

  @Override
  public boolean reloadOnDisplay() {
    return false;
  }
  
  private void startUpdateData() {
    taskFacade.submit(new DataUpdaterTask());
  }
  
  protected abstract DATA loadData(Context context) throws Exception;
  
  protected abstract void updateData(DATA data) throws Exception;
  
  protected void showError(Throwable error) {
    LOGGER.error("An error append", error);
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
    return rootPane;
  }

  protected void setExtraData() {
    // Override to use
  }

  protected DATA getData() {
    return view.getData();
  }

  protected void setData(DATA data) {
    view.setData(data);
  }
}

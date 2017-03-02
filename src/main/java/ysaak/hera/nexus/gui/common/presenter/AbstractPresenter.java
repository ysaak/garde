package ysaak.hera.nexus.gui.common.presenter;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.ViewLoader;
import ysaak.hera.nexus.gui.events.view.CloseFormEvent;
import ysaak.hera.nexus.gui.events.view.OpenFormEvent;
import ysaak.hera.nexus.service.event.EventFacade;

public abstract class AbstractPresenter<DATA> implements Presenter {
  
  @Autowired
  protected EventFacade eventFacade;

  @Autowired
  protected ViewLoader viewLoader;

  protected Context currentContext = null;
  
  @Override
  public void init() {
    eventFacade.register(this);
  }

  @Override
  public abstract Node getView();
  
  @Override
  public void startLoadData(final Context context) {
    currentContext = context;

    Service<DATA> loadService = new Service<DATA>() {
      @Override
      protected Task<DATA> createTask() {
        return new Task<DATA>() {

          @Override
          protected DATA call() throws Exception {
            return loadData(context);
          }
        };
      }
    };
    
    loadService.setOnSucceeded(value -> setData(loadService.getValue()));
    loadService.setOnFailed(value -> showError(loadService.getException()));
    
    loadService.start();
  }
  
  protected void startUpdateData() {
    Service<Void> storeService = new Service<Void>() {
      @Override
      protected Task<Void> createTask() {
        return new Task<Void>() {

          @Override
          protected Void call() throws Exception {
            updataData(getData());
            return null;
          }
        };
      }
    };
    
    storeService.setOnSucceeded(value -> closeView());
    storeService.setOnFailed(value -> showError(storeService.getException()));
    storeService.start();
  }
  
  protected abstract DATA loadData(Context context) throws Exception;
  protected abstract void setData(DATA data);
  
  protected abstract void updataData(DATA data) throws Exception;
  protected abstract DATA getData();
  
  protected void showError(Throwable error) {
    error.printStackTrace();
  }
  
  protected void closeView() {
    eventFacade.unregister(this);
    eventFacade.post(new CloseFormEvent(this));
  }
  
  protected void fireOpenFormRequest(String viewCode, Context context) {
    eventFacade.post(new OpenFormEvent(viewCode, context));
  }
}

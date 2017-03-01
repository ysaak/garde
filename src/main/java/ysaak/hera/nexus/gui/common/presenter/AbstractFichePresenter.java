package ysaak.hera.nexus.gui.common.presenter;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBarAction;
import ysaak.hera.nexus.gui.common.view.View;
import ysaak.hera.nexus.gui.common.view.ViewListener;

public abstract class AbstractFichePresenter<DATA, VIEW extends View<DATA>> extends AbstractPresenter<DATA> implements Presenter {
  
  private final BorderPane pane;
  
  protected VIEW view;
  
  protected final ButtonBar buttonBar;
  
  public AbstractFichePresenter(ButtonBar buttonBar) {
    super();
    
    this.pane = new BorderPane();
    this.buttonBar = buttonBar;
    if (this.buttonBar != null) {
      this.buttonBar.setListener(action -> {
        if (action == ButtonBarAction.FINISH || action == ButtonBarAction.CANCEL) {
          closeView();
        }
        else if (action == ButtonBarAction.SAVE) {
          startUpdateData();
        }
      });
    }
  }
  
  @Override
  public void init() {
    super.init();
    
    this.view = initView();
    this.pane.setCenter(this.view.getView());
    
    if (buttonBar != null) {
      this.pane.setBottom(buttonBar);
      
      buttonBar.hasChangedProperty().bind(this.view.hasChangedProperty());
      buttonBar.isValidProperty().bind(this.view.isValidProperty());
    }
    
    view.setListener(new ViewListener() {
      
      @Override
      public void openForm(String viewCode, Context context) {
        fireOpenFormRequest(viewCode, context);
      }
    });
  }
  
  protected abstract VIEW initView();

  @Override
  public Node getView() {
    return pane;
  }

  @Override
  protected DATA getData() {
    return view.getData();
  };
  
  @Override
  protected void setData(DATA data) {
    view.setData(data);
  };
}

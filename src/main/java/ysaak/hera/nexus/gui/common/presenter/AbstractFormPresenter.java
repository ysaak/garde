package ysaak.hera.nexus.gui.common.presenter;

import javafx.scene.Node;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.ViewLoader;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBarAction;
import ysaak.hera.nexus.gui.common.components.ModulePane;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;
import ysaak.hera.nexus.gui.common.view.ViewListener;

public abstract class AbstractFormPresenter<DATA, VIEW extends AbstractFormView<DATA>> extends AbstractPresenter<DATA> implements Presenter {
  
  private ModulePane pane;
  
  protected VIEW view;

  public AbstractFormPresenter() {}

  @Deprecated
  public AbstractFormPresenter(ButtonBar buttonBar) {
    super();
  }
  
  @Override
  public void init() {
    super.init();

    pane = new ModulePane();
    
    this.view = initView();
    this.pane.setCenter(this.view.getView());


    pane.setTitle(view.getTitle());
    pane.setToolbarComponents(view.getToolbarComponents());
    pane.setButtonBar(view.getButtonBar());

    if (view.getButtonBar() != null) {
      view.getButtonBar().hasChangedProperty().bind(this.view.hasChangedProperty());
      view.getButtonBar().isValidProperty().bind(this.view.isValidProperty());

      view.getButtonBar().setListener(action -> {
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
  
  protected abstract VIEW initView();

  @Override
  public Node getView() {
    return pane.getView();
  }

  @Override
  protected DATA getData() {
    return view.getData();
  }
  
  @Override
  protected void setData(DATA data) {
    view.setData(data);
  }
}

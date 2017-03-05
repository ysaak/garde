package ysaak.hera.nexus.gui.common.presenter;

import javafx.scene.Node;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.ViewLoader;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.gui.common.buttonbar.ButtonBarAction;
import ysaak.hera.nexus.gui.common.components.ModulePane;
import ysaak.hera.nexus.gui.common.view.AbstractFormView;
import ysaak.hera.nexus.gui.common.view.ViewListener;

public abstract class AbstractFormPresenter<DATA, VIEW extends AbstractFormView<DATA>> extends AbstractPresenter<DATA, VIEW> implements Presenter {
  
  private ModulePane pane;

  public AbstractFormPresenter() {}

  @Override
  public void init() {
    super.init();

    pane = new ModulePane();
    
    this.view = initView();
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

  @Override
  public Node getView() {
    return pane.getView();
  }
}

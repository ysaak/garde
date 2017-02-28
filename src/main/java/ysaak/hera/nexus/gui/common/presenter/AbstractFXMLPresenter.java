package ysaak.hera.nexus.gui.common.presenter;

import ysaak.hera.nexus.gui.common.buttonbar.ButtonBar;
import ysaak.hera.nexus.gui.common.view.View;

import java.io.IOException;


public abstract class AbstractFXMLPresenter<D, V extends View<D>> extends AbstractFichePresenter<D, V> {

  private final String fxml;
  
  public AbstractFXMLPresenter(String fxml, ButtonBar buttonBar) throws IOException {
    super(buttonBar);
    this.fxml = fxml;
  }
  
  @Override
  protected V initView() {
    try {
      return viewLoader.loadView(fxml);
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

}

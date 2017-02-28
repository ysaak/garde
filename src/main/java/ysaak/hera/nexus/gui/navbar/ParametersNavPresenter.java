package ysaak.hera.nexus.gui.navbar;

import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.NavbarComponent;
import ysaak.hera.nexus.gui.common.components.button.NavbarButton;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;

@Component
@NavbarComponent
public class ParametersNavPresenter extends AbstractPresenter<Void> {
  
  private final NavbarButton parametersButton;

  public ParametersNavPresenter() {
    super();
    
    parametersButton = new NavbarButton(MaterialDesignIcon.SETTINGS);
  }

  @Override
  public Node getView() {
    return parametersButton;
  }

  @Override
  protected Void loadData(Context context) throws Exception { return null; }

  @Override
  protected void setData(Void data) {/**/}

  @Override
  protected void updataData(Void data) throws Exception {/**/}

  @Override
  protected Void getData() { return null; }

}

package ysaak.hera.nexus.gui.navbar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import ysaak.hera.nexus.business.service.child.ChildService;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.annotation.NavbarComponent;
import ysaak.hera.nexus.gui.common.components.button.NavbarButton;
import ysaak.hera.nexus.gui.common.presenter.AbstractPresenter;

@Component
@NavbarComponent
public class BirthdayNavPresenter extends AbstractPresenter<Boolean> {
  
  @Autowired
  private ChildService childService;
  
  private final NavbarButton birthdayButton;

  public BirthdayNavPresenter() {
    super();
    
    birthdayButton = new NavbarButton(MaterialDesignIcon.CAKE_VARIANT);
  }

  @Override
  public Node getView() {
    return birthdayButton;
  }

  @Override
  protected Boolean loadData(Context context) throws Exception {
    return childService.hasBirthDayNearby();
  }

  @Override
  protected void setData(Boolean data) {
    if (data != null && data.booleanValue()) {
      birthdayButton.setIconBaseColor(Color.RED);
    }
    else {
      birthdayButton.setIconBaseColor(NavbarButton.DEFAULT_ICON_BASE_COLOR);
    }
  }

  @Override
  protected void updataData(Boolean data) throws Exception {/**/}

  @Override
  protected Boolean getData() { return null; }

}

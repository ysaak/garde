package ysaak.hera.nexus.gui.common;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ysaak.hera.nexus.gui.common.view.View;

import java.io.IOException;
import java.net.URL;

@Component
public final class ViewLoader implements ApplicationContextAware {

  private static final String FXML_BASE_PATH = "/fxml/";

  private ApplicationContext applicationContext = null;

  public <DATA, T extends View<DATA>> T loadView(String fxml) throws IOException {
    final FXMLLoader loader = getLoader(fxml);
    loader.load();
    return loader.getController();
  }

  private FXMLLoader getLoader(String fxml) {
    FXMLLoader loader = new FXMLLoader();
    if (applicationContext != null) {
      loader.setControllerFactory(param -> applicationContext.getBean(param));
    }

    final URL location = getClass().getResource(FXML_BASE_PATH + fxml + ".fxml");
    loader.setLocation(location);
    
    return loader;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}

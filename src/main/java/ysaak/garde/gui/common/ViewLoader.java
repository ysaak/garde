package ysaak.garde.gui.common;

import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ysaak.garde.gui.common.view.View;
import ysaak.garde.service.translation.I18n;

import java.io.IOException;
import java.net.URL;

@Component
public final class ViewLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(ViewLoader.class);

  private static final String FXML_BASE_PATH = "/fxml/";

  public <T> T loadView(Class<T> clazz) throws RuntimeException {
    final T instance;
    try {
      instance = clazz.newInstance();
    }
    catch (Exception e) {
      LOGGER.error("Error while creating instance of class " + clazz.getSimpleName(), e);
      throw new RuntimeException("Error while creating instance of class " + clazz.getSimpleName(), e);
    }

    if (instance instanceof View<?>) {
      ((View<?>) instance).initialize();
    }

    return instance;
  }

  //public <DATA, T extends View<DATA>> T loadView(String fxml) throws IOException {
  public <T> T loadView(String fxml) throws RuntimeException {
    final FXMLLoader loader = getLoader(fxml);
    try {
      loader.load();
    }
    catch (IOException e) {
      LOGGER.error("Error while loading FXML " + fxml, e);
      throw new RuntimeException("Error while loading FXML " + fxml, e);
    }
    return loader.getController();
  }

  private FXMLLoader getLoader(String fxml) {
    FXMLLoader loader = new FXMLLoader();
    loader.setResources(I18n.getBundle());

    final URL location = getClass().getResource(FXML_BASE_PATH + fxml + ".fxml");
    loader.setLocation(location);
    
    return loader;
  }
}

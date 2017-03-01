package ysaak.hera.nexus.gui.common;

import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ysaak.hera.nexus.gui.common.view.TranslationAware;
import ysaak.hera.nexus.gui.common.view.View;
import ysaak.hera.nexus.service.translation.TranslationFacade;

import java.io.IOException;
import java.net.URL;

@Component
public final class ViewLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(ViewLoader.class);

  private static final String FXML_BASE_PATH = "/fxml/";

  @Autowired
  private TranslationFacade translationFacade;

  public <T> T loadView(Class<T> clazz) throws RuntimeException {
    final T instance;
    try {
      instance = clazz.newInstance();
    }
    catch (Exception e) {
      LOGGER.error("Error while creating instance of class " + clazz.getSimpleName(), e);
      throw new RuntimeException("Error while creating instance of class " + clazz.getSimpleName(), e);
    }
    initializeViewComponents(instance);

    if (instance instanceof View<?>) {
      ((View<?>) instance).initialize();
    }

    return instance;
  }

  //public <DATA, T extends View<DATA>> T loadView(String fxml) throws IOException {
  public <T> T loadView(String fxml) throws IOException {
    final FXMLLoader loader = getLoader(fxml);
    loader.load();
    return loader.getController();
  }

  private <T> void initializeViewComponents(T instance) {

    if (instance instanceof TranslationAware) {
      ((TranslationAware) instance).setTranslationFacade(translationFacade);
    }
  }

  private FXMLLoader getLoader(String fxml) {
    FXMLLoader loader = new FXMLLoader();
    if (translationFacade != null) {
      loader.setResources(translationFacade.getBundle());
    }

    final URL location = getClass().getResource(FXML_BASE_PATH + fxml + ".fxml");
    loader.setLocation(location);
    
    return loader;
  }
}

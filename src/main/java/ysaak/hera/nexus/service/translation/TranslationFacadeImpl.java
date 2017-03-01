package ysaak.hera.nexus.service.translation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Service
public class TranslationFacadeImpl implements TranslationFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(TranslationFacade.class);

  private final ResourceBundle bundle = ResourceBundle.getBundle("lang/messages");

  @Override
  public String get(String key) {
    try {
      return bundle.getString(key);
    }
    catch (MissingResourceException e) {
      LOGGER.warn("Translation key not found : " + key);
      return "??" + key + "??";
    }

  }

  @Override
  public String get(String key, Object... args) {
    final String text = this.get(key);
    return MessageFormat.format(text, (Object[]) args);
  }

  @Override
  public ResourceBundle getBundle() {
    return bundle;
  }
}

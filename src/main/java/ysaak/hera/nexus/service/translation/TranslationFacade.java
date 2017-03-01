package ysaak.hera.nexus.service.translation;


import java.util.ResourceBundle;

public interface TranslationFacade {

  /**
   * Find a translation using key
   * @param key translation key
   * @return Translated text
   */
  String get(String key);

  /**
   * Find a translation using key
   * @param key translation key
   * @param args arguments
   * @return Translated text
   */
  String get(String key, Object ...args);

  /**
   * Returns the resource bundle used by the facade
   * @return A resource bundle
   */
  ResourceBundle getBundle();
}

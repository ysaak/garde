package ysaak.hera.nexus.service.translation;


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
}

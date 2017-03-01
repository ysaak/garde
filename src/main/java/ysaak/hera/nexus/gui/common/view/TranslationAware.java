package ysaak.hera.nexus.gui.common.view;

import ysaak.hera.nexus.service.translation.TranslationFacade;

/**
 * Interface used to automatically inject translation facade into view
 */
public interface TranslationAware {
  void setTranslationFacade(TranslationFacade translationFacade);
}

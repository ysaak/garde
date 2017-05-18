package ysaak.garde.gui;

import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ysaak.garde.gui.common.Context;
import ysaak.garde.gui.common.Contexts;
import ysaak.garde.gui.common.ViewLoader;
import ysaak.garde.gui.common.annotation.Module;
import ysaak.garde.gui.common.presenter.Presenter;
import ysaak.garde.gui.events.view.CloseFormEvent;
import ysaak.garde.gui.events.view.OpenFormEvent;
import ysaak.garde.gui.leftpanel.LeftPanelPresenter;
import ysaak.garde.service.EventFacade;
import ysaak.garde.service.task.TaskFacade;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainViewController {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainViewController.class);
  
  private static final String FORMS_PACKAGE = MainViewController.class.getPackage().getName();

  @Autowired
  private ViewLoader viewLoader;

  @Autowired
  private ApplicationContext applicationContext;

  private final LinkedList<Presenter> openedViews = new LinkedList<>();

  private final MainView mainView;

  private Class<? extends Presenter> rootForm = null;
  private final Map<String, Class<? extends Presenter>> formsMap;

  public MainViewController(MainView mainView) {
    this.mainView = mainView;

    formsMap = loadModules();
    
    if (rootForm == null) {
      LOGGER.error("No root forms");
      throw new IllegalStateException("No root forms configured");
    }
  }

  public void init() {
    EventFacade.register(this);

    // Load left panel
    LeftPanelPresenter leftPanelPresenter;
    try {
      leftPanelPresenter = loadPresenter(LeftPanelPresenter.class);
    }
    catch (Exception e) {
      LOGGER.error("Error while loading left panel presenter", e);
      leftPanelPresenter = null;
    }

    if (leftPanelPresenter != null) {
      mainView.setLeftPane(leftPanelPresenter.getView());
    }

    showAppRootView();
  }

  @SuppressWarnings("unchecked")
  private Map<String, Class<? extends Presenter>> loadModules() {

    Map<String, Class<? extends Presenter>> forms = new HashMap<>();

    try {
      ClassPath classpath = ClassPath.from(getClass().getClassLoader());

      for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(FORMS_PACKAGE)) {
        Class<?> clazz = classInfo.load();
        
        if (clazz.isAnnotationPresent(Module.class) && Presenter.class.isAssignableFrom(clazz)) {

          final Module an = clazz.getAnnotation(Module.class);

          if (an.root()) {
            if (rootForm != null) {
              LOGGER.error("Multiple root forms {} and {}", rootForm.getName(), clazz.getName());
            }

            rootForm = (Class<? extends Presenter>) clazz;
          }

          forms.put(an.value(), (Class<? extends Presenter>) clazz);
        }
      }
    }
    catch (IOException e) {
      LOGGER.error("Error while loading forms", e);
    }

    return forms;
  }

  private void showAppRootView() {
    if (rootForm != null) {
      showView(rootForm, Contexts.emptyContext());
    }
  }

  @Subscribe
  public void onOpenFormEvent(OpenFormEvent event) {
    final Class<? extends Presenter> view = formsMap.get(event.getViewCode());

    if (view != null) {
      showView(view, event.getContext());
    }
    else {
      LOGGER.error("No form with code '" + event.getViewCode() + "' is registered");
    }
  }

  @Subscribe
  public void onCloseFormEvent(CloseFormEvent event) {
    if (event.getPresenter() != null) {
      LOGGER.debug("Receiving request to close presenter named {}", event.getPresenter().getClass().getName());
      closeView(event.getPresenter());
    }
    else if (event.isCloseWholeContext()) {
      LOGGER.debug("Receiving request to close the whole context");
      openedViews.clear();
      showAppRootView();
    }
  }

  private <T> T loadPresenter(Class<T> presenterClazz) throws Exception {
    final T presenter = presenterClazz.newInstance();
    applicationContext.getAutowireCapableBeanFactory().autowireBean(presenter);

    if (presenter instanceof Presenter) {
      ((Presenter) presenter).init();
    }

    return presenter;
  }

  private void showView(Class<? extends Presenter> presenterClazz, Context context) {
    LOGGER.debug("Opening presenter {} with context {}", presenterClazz.getName(), context.toString());

    try {
      final Presenter presenter = loadPresenter(presenterClazz);
      openedViews.add(presenter);

      mainView.setModulePane(presenter.getView());

      // Load data
      presenter.startLoadData(context);
    }
    catch (final Exception e) {
      LOGGER.error("Error while loading view", e);
    }
  }

  private void closeView(Presenter presenter) {
    LOGGER.debug("Closing presenter {}", presenter.getClass().getName());

    final int index = openedViews.indexOf(presenter);
    final int lastIndex = openedViews.size() - 1;

    // Remove the last entry
    openedViews.remove(index);

    if (openedViews.size() > 0) {

      if (index == lastIndex) {
        // Display top element if removed presenter was the last one
        Presenter last = openedViews.getLast();
        mainView.setModulePane(last.getView());

        if (last.reloadOnDisplay()) {
          last.startReloadData();
        }
      }
    }
    else {
      // Nothing to display, show root pane
      showAppRootView();
    }
  }
}

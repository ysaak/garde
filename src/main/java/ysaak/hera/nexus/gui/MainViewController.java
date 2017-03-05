package ysaak.hera.nexus.gui;

import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.ClassPath;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ysaak.hera.nexus.gui.common.Context;
import ysaak.hera.nexus.gui.common.Contexts;
import ysaak.hera.nexus.gui.common.ViewLoader;
import ysaak.hera.nexus.gui.common.annotation.Fiche;
import ysaak.hera.nexus.gui.common.presenter.Presenter;
import ysaak.hera.nexus.gui.common.view.ViewListener;
import ysaak.hera.nexus.gui.events.view.CloseFormEvent;
import ysaak.hera.nexus.gui.events.view.OpenFormEvent;
import ysaak.hera.nexus.gui.leftpanel.LeftPanelPresenter;
import ysaak.hera.nexus.service.event.EventFacade;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainViewController {
  private static final Logger LOGGER = LoggerFactory.getLogger(MainViewController.class);
  
  private static final String FORMS_PACKAGE = MainViewController.class.getPackage().getName();

  @Autowired
  private EventFacade eventFacade;

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
    this.mainView.setListener(new ViewListener() {

      @Override
      public void openForm(String viewCode, Context context) {
        onOpenFormEvent(new OpenFormEvent(viewCode, context));
      }

      @Override
      public ViewLoader getViewLoader() {
        return viewLoader;
      }
    });

    eventFacade.register(this);
    eventFacade.register(mainView);

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

  @PreDestroy
  public void onDestroy() {
    eventFacade.unregister(mainView);
    eventFacade.unregister(this);
  }

  @SuppressWarnings("unchecked")
  private Map<String, Class<? extends Presenter>> loadModules() {

    Map<String, Class<? extends Presenter>> forms = new HashMap<>();

    try {
      ClassPath classpath = ClassPath.from(getClass().getClassLoader());

      for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClassesRecursive(FORMS_PACKAGE)) {
        Class<?> clazz = classInfo.load();
        
        if (clazz.isAnnotationPresent(Fiche.class) && Presenter.class.isAssignableFrom(clazz)) {

          final Fiche an = clazz.getAnnotation(Fiche.class);

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
      closeView(event.getPresenter());
    }
  }

  private <T extends Presenter> T loadPresenter(Class<T> presenterClazz) throws Exception {
    final T presenter = presenterClazz.newInstance();
    applicationContext.getAutowireCapableBeanFactory().autowireBean(presenter);

    presenter.init();

    return presenter;
  }

  private void showView(Class<? extends Presenter> presenterClazz, Context context) {
    try {
      final Presenter presenter = loadPresenter(presenterClazz);
      final Node node = presenter.getView();

      // Display node
      AnchorPane.setTopAnchor(node, 0.0);
      AnchorPane.setBottomAnchor(node, 0.0);
      AnchorPane.setLeftAnchor(node, 0.0);
      AnchorPane.setRightAnchor(node, 0.0);

      openedViews.add(presenter);

      mainView.setCenterNode(node);

      // Load data
      presenter.startLoadData(context);
    }
    catch (final Exception e) {
      e.printStackTrace();
    }
  }

  private void closeView(Presenter presenter) {

    final int index = openedViews.indexOf(presenter);
    final int lastIndex = openedViews.size() - 1;

    if (index == lastIndex) {
      // Remove the last entry
      openedViews.remove(index);
    }

    if (openedViews.size() > 0) {
      // Display top element
      Presenter last = openedViews.getLast();
      mainView.setCenterNode(last.getView());
    }
    else {
      // Nothing to display, show root pane
      showAppRootView();
    }
  }
}

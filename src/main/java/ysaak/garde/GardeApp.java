package ysaak.garde;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import ysaak.garde.business.MockInitializer;
import ysaak.garde.gui.MainView;
import ysaak.garde.gui.MainViewController;
import ysaak.garde.gui.common.ViewLoader;
import ysaak.garde.service.task.TaskFacade;

@Lazy
@SpringBootApplication
public class GardeApp extends Application {

  private static String[] savedArgs;

  private ConfigurableApplicationContext applicationContext;

  @Autowired
  private ViewLoader viewLoader;

  @Autowired
  private TaskFacade taskFacade;

  public static void main(String[] args) {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Garde!");

    savedArgs = args;
    Application.launch(GardeApp.class, args);
  }
  
  @Override
  public void init() throws Exception {
    applicationContext = SpringApplication.run(getClass(), savedArgs);
    applicationContext.getAutowireCapableBeanFactory().autowireBean(this);

    // Initialize mock data
    applicationContext.getBean(MockInitializer.class).init();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Garde!");

    final MainView mainView = viewLoader.loadView("mainLayout");
    final MainViewController controller = new MainViewController(mainView);
    applicationContext.getAutowireCapableBeanFactory().autowireBean(controller);

    final Scene scene = new Scene((Parent) mainView.getView());

    String css = getClass().getResource("/hera-styles.css").toExternalForm();
    scene.getStylesheets().add(css);


    controller.init();

    primaryStage.setScene(scene);
    primaryStage.centerOnScreen();
    primaryStage.setFullScreen(true);
    //primaryStage.sizeToScene();
    primaryStage.show();
  }

  @Override
  public void stop() throws Exception {
    taskFacade.shutdown();
    applicationContext.close();
  }
}

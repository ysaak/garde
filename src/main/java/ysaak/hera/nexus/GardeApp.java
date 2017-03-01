package ysaak.hera.nexus;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import ysaak.hera.nexus.business.MockInitializer;
import ysaak.hera.nexus.gui.MainView;
import ysaak.hera.nexus.gui.MainViewController;
import ysaak.hera.nexus.gui.common.ViewLoader;

@Lazy
@SpringBootApplication
public class GardeApp extends Application {

  private static String[] savedArgs;

  private ConfigurableApplicationContext applicationContext;

  @Autowired
  private ViewLoader viewLoader;

  public static void main(String[] args) {
    /*
    System.setProperty("prism.lcdtext", "false");
    System.setProperty("prism.text", "t2k");
    System.setProperty("prism.order", "d3d,j2d");
    System.setProperty("prism.vsync", "true");
    System.setProperty("prism.forceGPU", "true");
    */

    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.mennu.about.name", "Garde!");

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
    applicationContext.close();
  }
}

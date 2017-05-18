package ysaak.garde.gui.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import ysaak.garde.service.translation.I18n;

public final class Dialogs {

  public static void information(String title, String content) {
    information(title, null, content);
  }
  
  public static void information(String title, String header, String content) {
    showDialog(AlertType.INFORMATION, title, header, content, null);
  }
  
  public static void warning(String title, String content) {
    warning(title, null, content);
  }
  
  public static void warning(String title, String header, String content) {
    showDialog(AlertType.WARNING, title, header, content, null);
  }

  public static void error(String content) {
    error(I18n.get("common.error"), content);
  }

  public static void error(String title, String content) {
    error(title, null, content);
  }
  public static void error(String title, String content, Exception ex) {
    error(title, null, content, ex);
  }
  
  public static void error(String title, String header, String content) {
    error(title, header, content, null);
  }
  
  public static void error(String title, String header, String content, Exception ex) {
    showDialog(AlertType.ERROR, title, header, content, ex);
  }
  
  public static boolean confirm(String title, String content) {
    return confirm(title, null, content);
  }
  
  public static boolean confirm(String title, String header, String content) {
    Optional<ButtonType> result = showDialog(AlertType.CONFIRMATION, title, header, content, null);
    return result.isPresent() && result.get() == ButtonType.OK;
  }
  
  private static Optional<ButtonType> showDialog(AlertType type, String title, String header, String content, Exception ex) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);
    
    if (ex != null) {
      final Node exContent = createExpendableContent(ex);
      alert.getDialogPane().setExpandableContent(exContent);
    }
    
    return alert.showAndWait();
  }
  
  private static Node createExpendableContent(Exception ex) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    String exceptionText = sw.toString();

    Label label = new Label("The exception stacktrace was:");

    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);
    expContent.add(label, 0, 0);
    expContent.add(textArea, 0, 1);
    
    return expContent;
  }
}

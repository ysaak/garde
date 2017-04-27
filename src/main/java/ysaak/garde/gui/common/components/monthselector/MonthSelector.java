package ysaak.garde.gui.common.components.monthselector;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material.Material;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Month selector component
 */
public class MonthSelector extends HBox {
  private final DateTimeFormatter currentMonthLabelFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

  private final Label currentMonthLabel;

  private final JFXButton previousBtn;

  private final JFXButton nextBtn;

  public MonthSelector() {
    super(10.);
    setAlignment(Pos.CENTER_LEFT);

    previousBtn = new JFXButton("", new FontIcon(Material.KEYBOARD_ARROW_LEFT));

    nextBtn = new JFXButton("", new FontIcon(Material.KEYBOARD_ARROW_RIGHT));

    currentMonthLabel = new Label("");
    currentMonthLabel.setAlignment(Pos.CENTER);
    currentMonthLabel.setMinWidth(150.);

    getChildren().setAll(previousBtn, currentMonthLabel, nextBtn);
  }

  public void setMonth(LocalDate month) {
    currentMonthLabel.setText(currentMonthLabelFormatter.format(month));
  }

  public void setOnPreviousAction(EventHandler<ActionEvent> handler) {
    previousBtn.setOnAction(handler);
  }

  public void setOnNextAction(EventHandler<ActionEvent> handler) {
    nextBtn.setOnAction(handler);
  }
}

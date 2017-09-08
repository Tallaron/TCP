package de.tallaron.tcp.ui;

import de.tallaron.tcp.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

/**
 *
 * @author NW
 */
public abstract class ToggleButton {
    
    public static Button getNode(App app, Node n) {
        Button btnToggle = new Button(((TitledPane)n).getText());
        btnToggle.setPrefWidth(150);
        btnToggle.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                app.setContentFocus(n);
            }
        });
        return btnToggle;
    }
    
}

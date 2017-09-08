package de.tallaron.tcp.ui;

import de.tallaron.tcp.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author NW
 */
public abstract class ConnectButton {
    
    public static HBox getNode(App app) {
        Button btnConnect = new Button("Connect");
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(5, 5, 0, 5));
        box.setPrefHeight(40);
        box.setSpacing(5);

        btnConnect.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                app.connect();
            }
        });
        box.getChildren().addAll(btnConnect);
        return box;
    }
    
}

package de.tallaron.tcp.ui;

import de.tallaron.tcp.App;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author NW
 */
public abstract class DisconnectButton {
    
    public static HBox getNode(App app) {
        Label currentUserLabel = new Label();
        Button btnDisconnect = new Button("Disconnect");
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(5, 5, 0, 5));
        box.setPrefHeight(40);
        box.setSpacing(5);

        currentUserLabel.setText("User: "+app.getTc().getUser().getName());
        btnDisconnect.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                app.disconnect();
            }
        });
        box.getChildren().addAll(currentUserLabel, btnDisconnect);
        return box;
    }
    
}

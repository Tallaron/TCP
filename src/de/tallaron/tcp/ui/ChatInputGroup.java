package de.tallaron.tcp.ui;

import de.tallaron.tcp.controller.TwitchController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ChatInputGroup {


    
    public static Node create(TwitchController tc) {
        TextField input = new TextField();
        Button send = new Button("Send");
        HBox box = new HBox(input, send);
        box.setSpacing(5);
        HBox.setHgrow(input, Priority.ALWAYS);
        
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tc.sendChatMessage( input.getText() );
                input.setText("");
            }
        });
        
        input.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tc.sendChatMessage( input.getText() );
                input.setText("");
            }
        });
        
        return box;
    }
    
    

    
}

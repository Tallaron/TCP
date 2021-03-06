/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tallaron.tcp.ui;

import de.tallaron.tcp.controller.ChannelController;
import de.tallaron.tcp.controller.TwitchController;
import de.tallaron.tcp.entity.Status;
import de.tallaron.tcp.lambda.TCPCaller;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Administrator
 */
public class ChannelStatusListItem {
    
    public static HBox getNode(TwitchController tc, Status s) {
        HBox box = new HBox();
        box.setPadding(new Insets(5));
        StackPane nodeStack = new StackPane();
        
        Label normField = new Label(s.toString());
        normField.setMaxWidth(Double.MAX_VALUE);
        TextField editField = new TextField(s.toString());

        TCPCaller toggleVisibility = () -> nodeStack.getChildren().forEach( (e) -> e.setVisible(!e.isVisible()) ); //ListItem Scope
        TCPCaller deleteStatus = () -> ChannelController.removeStatusFromCollection(s, tc); //Object Scope
        TCPCaller updateStatus = () -> ChannelController.updateStatus(s, tc, editField.getText()); //Object Scope
        TCPCaller selectStatus = () -> tc.changeChannelStatus(s); //Twitch Scope
        
        HBox norm = new HBox(
            createButton(tc, "+", true, selectStatus),
            normField,
            createButton(tc, "E", true, toggleVisibility),
            createButton(tc, "D", true, deleteStatus)
        );
        HBox.setHgrow(normField, Priority.ALWAYS);
        norm.setPadding(new Insets(0,0,0,0));
        norm.setSpacing(3);
        norm.setVisible(true);
        
        HBox edit = new HBox(
            createButton(tc, "", false, () -> {}),
            editField,
            createButton(tc, "S", true, updateStatus),
            createButton(tc, "X", true, toggleVisibility)
        );
        HBox.setHgrow(editField, Priority.ALWAYS);
        edit.setPadding(new Insets(0,0,0,0));
        edit.setSpacing(3);
        edit.setVisible(false);
                
        nodeStack.getChildren().addAll(norm,edit);
        
        box.getChildren().add(nodeStack);
        HBox.setHgrow(nodeStack, Priority.ALWAYS);
        return box;
    }

    private static Button createButton(TwitchController tc, String btnText, boolean visible, TCPCaller caller) {
        Button btn = new Button(btnText);
        btn.setMinWidth(25);
        btn.setPrefWidth(25);
        btn.setVisible(visible);
        btn.setOnAction((ActionEvent event) -> {
            caller.call();
        });
        return btn;
    }
    
}

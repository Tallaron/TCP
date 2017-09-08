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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
        
        TCPCaller toggleVisibility = () -> nodeStack.getChildren().forEach( (e) -> e.setVisible(!e.isVisible()) );
        TCPCaller deleteStatus = () -> ChannelController.removeStatusFromCollection(s, tc);
        
        Label normField = new Label(s.toString());
        TextField editField = new TextField(s.toString());
        
        HBox norm = new HBox(
            createButton(tc, "+", true, () -> System.out.println("SELECT")),
            createNode(normField),
            createButton(tc, "E", true, toggleVisibility),
            createButton(tc, "D", true, deleteStatus)
        );
        norm.setPadding(new Insets(0,0,0,0));
        norm.setSpacing(5);
        norm.setVisible(true);
        
        HBox edit = new HBox(
            createButton(tc, "", false, () -> {}),
            createNode(editField),
            createButton(tc, "S", true, () -> System.out.println("SAVE")),
            createButton(tc, "X", true, toggleVisibility)
        );
        edit.setPadding(new Insets(0,0,0,0));
        edit.setSpacing(5);
        edit.setVisible(false);
                
        
        nodeStack.getChildren().addAll(norm,edit);
        
        box.getChildren().add(nodeStack);
        return box;
    }

    private static Button createButton(TwitchController tc, String btnText, boolean visible, TCPCaller caller) {
        Button btn = new Button(btnText);
        btn.setPrefWidth(30);
        btn.setVisible(visible);
        btn.setOnAction((ActionEvent event) -> {
            caller.call();
        });
        return btn;
    }
    
    private static Node createNode(Node n) {
        ((Control)n).setPrefWidth(250);
        return n;
    }
    
}

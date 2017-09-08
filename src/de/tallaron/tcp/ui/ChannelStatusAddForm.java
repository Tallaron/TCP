package de.tallaron.tcp.ui;

import de.tallaron.tcp.controller.ChannelController;
import de.tallaron.tcp.controller.TwitchController;
import de.tallaron.tcp.entity.Status;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ChannelStatusAddForm {
    
    public static HBox getNode(TwitchController tc) {
        HBox box = new HBox();
        box.setSpacing(5);
        TextField input = new TextField();
        input.setPrefWidth(250);
        Button btnAddStatus = new Button("Add Status");
        
        btnAddStatus.setOnAction((ActionEvent event) -> {
            if(input.getText().length()>0) {
                ChannelController.addStatusToCollection(new Status(input.getText()), tc);
            }
        });

        box.getChildren().addAll(input, btnAddStatus);
        return box;
    }

}

package de.tallaron.tcp.ui;

import de.tallaron.tcp.controller.ChannelController;
import de.tallaron.tcp.controller.TwitchController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class ChannelStatusPane {

    public static TitledPane getNode(TwitchController tc) {
        return getNode(tc, null);
    }
    
    public static TitledPane getNode(TwitchController tc, String title) {
        return getNode(tc, title, true);
    }
    
    public static TitledPane getNode(TwitchController tc, String title, boolean enabled) {
        TitledPane pane = new TitledPane();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(
            ChannelStatusAddForm.getNode(tc),
            ChannelStatusList.getNode(tc)
        );
        
        pane.setText(title != null ? title : "");
        pane.setPadding(new Insets(5));
        pane.setCollapsible(false);
        pane.setMinHeight(250);
        pane.setDisable(!enabled);
        pane.setContent(vbox);
        return pane;
    }
    
}

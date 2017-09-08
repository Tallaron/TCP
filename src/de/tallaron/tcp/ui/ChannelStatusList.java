package de.tallaron.tcp.ui;

import de.tallaron.tcp.controller.TwitchController;
import de.tallaron.tcp.entity.Status;
import javafx.scene.layout.VBox;

public class ChannelStatusList {

    public static VBox getNode(TwitchController tc) {
        VBox box = new VBox();
        
        for(Status status : tc.getChannel().getStatusCollection()) {
            box.getChildren().add( ChannelStatusListItem.getNode(tc, status) );
        }
        
        return box;
        
    }
    
}

package de.tallaron.tcp.ui;

import de.tallaron.tcp.controller.TwitchController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChannelStatusPane {
    
    TitledPane tp = null;
    ScrollPane sp = null;

    public ChannelStatusPane(String title, boolean enabled, TwitchController tc) {
        tp = new TitledPane();

        sp = new ScrollPane( new VBox());
        sp.setFitToWidth(true);

        VBox wrapper = new VBox( ChannelStatusAddForm.getNode(tc), sp );
        wrapper.setSpacing(5);
        VBox.setVgrow(sp, Priority.ALWAYS);
        
        tp.setText(title != null ? title : "");
        tp.setPadding(new Insets(5));
        tp.setCollapsible(false);
        tp.setMinHeight(250);
        tp.setDisable(!enabled);
        tp.setContent(wrapper);
    }
    
    public Node getNode() {
        return tp;   
    }

    public TitledPane getTp() {
        return tp;
    }

    public void setTp(TitledPane tp) {
        this.tp = tp;
    }

    public ScrollPane getSp() {
        return sp;
    }

    public void setSp(ScrollPane sp) {
        this.sp = sp;
    }
    
    
    
}

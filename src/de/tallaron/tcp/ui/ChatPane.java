package de.tallaron.tcp.ui;

import de.tallaron.tcp.controller.TwitchController;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ChatPane {
    
    private TitledPane tp = null;
    private BorderPane bp = null;
    private ScrollPane sp = null;

    public ChatPane(String title, boolean enabled, TwitchController tc) {
        sp = new ScrollPane( new VBox() );
        sp.setFitToWidth(true);
        
        VBox wrapper = new VBox( sp, ChatInputGroup.create(tc) );
        wrapper.setSpacing(5);
        VBox.setVgrow(sp, Priority.ALWAYS);
        
        tp = new TitledPane();
        tp.setText(title != null ? title : "");
        tp.setContent( wrapper );
        tp.setPadding(new Insets(5));
        tp.setCollapsible(false);
        tp.setMinHeight(250);
        tp.setDisable(!enabled);
    }
    
    public void addLine(Node line) {
        ((VBox)sp.getContent()).getChildren().add(line);
        sp.layout();
        sp.setVvalue(1.0);
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

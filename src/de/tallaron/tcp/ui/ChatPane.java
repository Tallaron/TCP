package de.tallaron.tcp.ui;

import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public abstract class ChatPane extends ContentPane {

    public static TitledPane getNode() {
        return getNode(null);
    }
    
    public static TitledPane getNode(String title) {
        return getNode(title, true);
    }

    public static TitledPane getNode(String title, boolean enabled) {
        TitledPane pane = new TitledPane();
        pane.setText(title != null ? title : "");
        pane.setPadding(new Insets(5));
        pane.setCollapsible(false);
        pane.setMinHeight(250);
        pane.setDisable(!enabled);
        pane.setContent(new VBox());
        return pane;
    }
    
}

package de.tallaron.tcp.ui;

import javafx.geometry.Insets;
import javafx.scene.control.TitledPane;

public class UserPane extends ContentPane {
    
    public static TitledPane getNode(String title, boolean enabled) {
        TitledPane pane = new TitledPane();
        pane.setText(title != null ? title : "");
        pane.setPadding(new Insets(5));
        pane.setCollapsible(false);
        pane.setMinHeight(250);
        pane.setPrefWidth(200);
        pane.setDisable(!enabled);
        return pane;
    }
    
}

package de.tallaron.tcp.ui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ChatLine {
    
    public static Node of(String name, String msg) {
        Text n = new Text(name);
        n.setStyle("-fx-font-weight: bold");
        Text m = new Text(": "+msg);
        m.setStyle("-fx-font-weight: normal");

        HBox box = new HBox();
        box.setPadding(new Insets(0,5,5,5));
        box.getChildren().addAll( new TextFlow(n,m) );
        return box;
    }
    
}

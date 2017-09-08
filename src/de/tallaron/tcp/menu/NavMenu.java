package de.tallaron.tcp.menu;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;


public class NavMenu {
    
    private final TitledPane NAV_MENU = new TitledPane();
    private final VBox BTN_CONTAINER = new VBox();

    public NavMenu() {
        NAV_MENU.setCollapsible(false);
        NAV_MENU.setText("Navigation");
        NAV_MENU.setContent(BTN_CONTAINER);
        NAV_MENU.setPadding(new Insets(5));
        NAV_MENU.setDisable(true);
    }
    
    public void addElements(Node... nodes) {
        for(Node n : nodes) {
            addElement(n);
        }
    }
    
    public void addElement(Node n) {
        if(!BTN_CONTAINER.getChildren().contains(n))
            BTN_CONTAINER.getChildren().add(n);
    }
    
    public TitledPane getMenu() {
        return NAV_MENU;
    }
    
}

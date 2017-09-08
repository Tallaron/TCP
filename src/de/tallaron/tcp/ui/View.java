package de.tallaron.tcp.ui;

import de.tallaron.tcp.controller.TwitchController;
import de.tallaron.tcp.entity.Channel;
import de.tallaron.tcp.entity.User;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class View {
    
    public static void drawUserInfo(TitledPane pane, User u) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(new Label(u.getName()+ " (ID: "+u.getId()+")"), 1, 0);
        
        grid.add(new Label("EMail:"), 0, 1);
        grid.add(new Label(u.getEmail()), 1, 1);
        
        grid.add(new Label("Bio:"), 0, 2);
        grid.add(new Label(u.getBio()), 1, 2);
        
        pane.setContent(grid);
    }

    public static void drawChannelInfo(TitledPane pane, Channel c) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);
        
        grid.add(new Label("Name:"), 0, 0);
        grid.add(new Label(c.getDisplayName()+ " (ID: "+c.getId()+")"), 1, 0);
        
        Hyperlink link = new Hyperlink(c.getUrl());
        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(link.getText()));
                } catch (IOException ignored) {
                } finally {
                    link.setVisited(false);
                }
            }
        });
        
        grid.add(new Label("URL:"), 0, 1);
        grid.add(link, 1, 1);

        grid.add(new Label("Game:"), 0, 2);
        grid.add(new Label(c.getGame()), 1, 2);

        grid.add(new Label("Desc.:"), 0, 3);
        grid.add(new Label(c.getDescription()), 1, 3);

        grid.add(new Label("Status:"), 0, 4);
        grid.add(new Label(c.getStatus()), 1, 4);

        grid.add(new Label("Views:"), 0, 5);
        grid.add(new Label(c.getViews()+""), 1, 5);

        grid.add(new Label("Follower:"), 0, 6);
        grid.add(new Label(c.getFollowers()+""), 1, 6);

        pane.setContent(grid);
    }
    
    public static void drawChannelStatus(TitledPane pane, TwitchController tc) {
        pane.setContent(
            new VBox(
                ChannelStatusAddForm.getNode(tc),
                ChannelStatusList.getNode(tc)
            )
        );
    }
    
    
}

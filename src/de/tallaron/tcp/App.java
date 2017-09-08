/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tallaron.tcp;

import de.tallaron.tcp.controller.TwitchController;
import de.tallaron.tcp.ui.ConnectButton;
import de.tallaron.tcp.menu.NavMenu;
import de.tallaron.tcp.ui.ChannelStatusPane;
import de.tallaron.tcp.ui.ContentPane;
import de.tallaron.tcp.ui.DisconnectButton;
import de.tallaron.tcp.ui.ToggleButton;
import de.tallaron.tcp.ui.UserPane;
import de.tallaron.tcp.ui.View;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Administrator
 */
public class App extends Application {
    
    private final BorderPane screen = new BorderPane();
    private TwitchController tc = new TwitchController();

    //CONTENTS
    //TOP
    //-
    //LEFT
    private final NavMenu NAV = new NavMenu();
    //CENTER
    private final StackPane STACK_PANE = new StackPane();
    private final TitledPane WELCOME_PANE = ContentPane.getNode("Welcome");
    private final TitledPane DISCONNECT_PANE = ContentPane.getNode("Bye Bye");
    private final TitledPane ERROR_PANE = ContentPane.getNode("Error");
    private final TitledPane USER_INFO_PANE = ContentPane.getNode("User Info");
    private final TitledPane CHANNEL_INFO_PANE = ContentPane.getNode("Channel::Info");
    private final TitledPane CHANNEL_STATUS_PANE = ContentPane.getNode("Channel::Status");
//    private final TitledPane CHANNEL_STATUS_PANE = ChannelStatusPane.getNode(this.getTc(), "Channel::Status");
    private final TitledPane CHAT_PANE = ContentPane.getNode("Chat");
    //RIGHT
    private final TitledPane USERS_PANE = UserPane.getNode("Users", false);
    //BOTTOM
    //-
    

    
    @Override
    public void start(Stage primaryStage) {
        init_tc();
        
        STACK_PANE.getChildren().addAll(
                WELCOME_PANE,
                DISCONNECT_PANE,
                ERROR_PANE,
                USER_INFO_PANE,
                CHANNEL_INFO_PANE,
                CHANNEL_STATUS_PANE,
                CHAT_PANE
        );
        STACK_PANE.setAlignment(Pos.TOP_CENTER);
        setContentFocus(WELCOME_PANE); //set default content for startup
        
        NAV.addElements(
            ToggleButton.getNode(this, USER_INFO_PANE),
            ToggleButton.getNode(this, CHANNEL_INFO_PANE),
            ToggleButton.getNode(this, CHANNEL_STATUS_PANE),
            ToggleButton.getNode(this, CHAT_PANE)
        );
        
        screen.setTop(ConnectButton.getNode(this));
        screen.setLeft(NAV.getMenu());
        screen.setCenter(STACK_PANE);
        screen.setRight(USERS_PANE);
        
        
        Scene scene = new Scene(screen, 800, 600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("Twitch Control Panel");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    
    public void connect() {
        disableTop();
        tc.auth();
        setContentFocus(WELCOME_PANE);
    }
    
    public void disconnect() {
        screen.setTop(ConnectButton.getNode(this));
        setContentFocus(DISCONNECT_PANE);
        init_tc();
        disableNav();
        disableRight();
        enableTop();
    }

    public void authDone() {
        if(!tc.isError()) {
            loadUser();
            screen.setTop( DisconnectButton.getNode(this) );
            loadChannel();
            enableTop();
            enableRight();
            enableNav();
        } else {
            screen.setTop(ConnectButton.getNode(this));
            setContentFocus(ERROR_PANE);
            init_tc();
            disableNav();
            enableTop();
        }
    }

    
    
    public void disableNav() {
        screen.getLeft().setDisable(true);
    }
    
    public void enableNav() {
        screen.getLeft().setDisable(false);
    }
    
    public void disableTop() {
        screen.getTop().setDisable(true);
    }
    
    public void enableTop() {
        screen.getTop().setDisable(false);
    }
    
    public void disableRight() {
        screen.getRight().setDisable(true);
    }
    
    public void enableRight() {
        screen.getRight().setDisable(false);
    }
    
    
    
    
    private void loadUser() {
        tc.loadUserData();
        View.drawUserInfo(USER_INFO_PANE, tc.getUser());
    }
    
    private void loadChannel() {
        tc.loadChannelData();
        View.drawChannelInfo(CHANNEL_INFO_PANE, tc.getChannel());
        View.drawChannelStatus(CHANNEL_STATUS_PANE, tc);
    }
    
    public void updateChannel() {
        loadChannel();
    }
    
    
    
    private void init_tc() {
        tc.setApp(this);
        tc.setError(false);
        tc.setAuthCode(null);
    }

    public TwitchController getTc() {
        return tc;
    }

    public void setTc(TwitchController tc) {
        this.tc = tc;
    }
    
    
    public void setContentFocus(Node n) {
        STACK_PANE.getChildren().forEach((e) -> ((TitledPane)e).setVisible( e.equals(n) ));
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

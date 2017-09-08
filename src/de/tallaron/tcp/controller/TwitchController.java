package de.tallaron.tcp.controller;

import de.tallaron.tcp.App;
import java.io.IOException;
import static de.tallaron.tcp.Settings.*;
import de.tallaron.tcp.entity.Channel;
import de.tallaron.tcp.entity.User;
import de.tallaron.tcp.util.TwitchAccessTokenListener;
import de.tallaron.tcp.util.TwitchAuthCodeListener;

public class TwitchController {

    private String authCode = null;
    private String authToken = null;
    private boolean error = false;
    private App app;
    private User user;
    private Channel channel;

    public void auth() {
        String url = KRAKEN_BASE_URL + "oauth2/authorize"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URL
                + "&response_type=code"
                + "&scope=user_read+channel_editor+chat_login"
                + "&force_verify="+FORCE_VERIFY;
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (IOException ignored) {
        }
        receiveAuthCode();
    }
    
    private void receiveAuthCode() {
        new Thread( new TwitchAuthCodeListener(this) ).start();
    }


    public void receiveAuthToken() {
        new Thread( new TwitchAccessTokenListener(this) ).start();
    }
    
    
    public void loadUserData() {
        setUser( UserController.createObj(this) );
    }
    
    public void loadChannelData() {
        setChannel( ChannelController.createObj(this) );
    }
    
    public void changeChannelStatus(String status) {
        ChannelController.changeStatus(status, this);
        app.updateChannel();
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    

}

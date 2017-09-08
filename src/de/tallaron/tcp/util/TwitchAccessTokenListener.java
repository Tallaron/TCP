/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tallaron.tcp.util;

import de.tallaron.tcp.controller.TwitchController;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import static de.tallaron.tcp.Settings.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Administrator
 */
public class TwitchAccessTokenListener implements Runnable {
    
    private TwitchController tc;

    public TwitchAccessTokenListener(TwitchController tc) {
        this.tc = tc;
    }

    
    @Override
    public void run() {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpPost httpPost = new HttpPost(KRAKEN_BASE_URL + "oauth2/token");
            httpPost.setEntity(new UrlEncodedFormEntity(getOAuthPostParams()));

            CloseableHttpResponse response2 = httpclient.execute(httpPost);
            HttpEntity entity2 = response2.getEntity();

            BufferedReader br = new BufferedReader(new InputStreamReader(entity2.getContent()));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("access_token")) {
                    JSONObject json = (JSONObject) JSONValue.parse(line);
                    if(json != null) {
                        tc.setAuthToken(json.get("access_token").toString());
                        break;
                    }
                }
            }
            Platform.runLater( () -> tc.getApp().authDone() );
        } catch (UnsupportedEncodingException ignored) {
        } catch (IOException ignored) {
        }

    }

    private List<NameValuePair> getOAuthPostParams() {
        List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("client_id", CLIENT_ID));
        nvps.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
        nvps.add(new BasicNameValuePair("code", tc.getAuthCode()));
        nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
        nvps.add(new BasicNameValuePair("redirect_uri", REDIRECT_URL));
        return nvps;
    }

    public TwitchController getTc() {
        return tc;
    }

    public void setTc(TwitchController tc) {
        this.tc = tc;
    }

    
    
}

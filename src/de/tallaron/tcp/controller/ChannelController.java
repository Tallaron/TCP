package de.tallaron.tcp.controller;

import org.json.simple.JSONObject;
import static de.tallaron.tcp.Settings.*;
import de.tallaron.tcp.entity.Channel;
import de.tallaron.tcp.entity.Status;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.fluent.Request;
import org.json.simple.JSONValue;

public class ChannelController {
    
    public static Channel createObj(TwitchController tc) {
        JSONObject data = loadData( tc.getAuthToken(), tc.getUser().getId() );
        Channel c = new Channel();
        c.setId(Integer.parseInt( data.get("_id").toString() ));
        c.setMature(Boolean.parseBoolean( data.get("mature").toString() ));
        c.setBroadCasterLanguage( data.get("broadcaster_language").toString() );
        c.setDescription( data.get("description").toString() );
        c.setDisplayName( data.get("display_name").toString() );
        c.setFollowers(Integer.parseInt( data.get("followers").toString() ));
        c.setGame( data.get("game").toString() );
        c.setLanguage( data.get("language").toString() );
        c.setName( data.get("name").toString() );
        c.setStatus( data.get("status").toString() );
        c.setUrl( data.get("url").toString() );
        c.setViews(Integer.parseInt( data.get("views").toString() ));
        c.setStatusCollection(loadStatusCollection(c));
        return c;
    }

    private static JSONObject loadData(String token, int channelId) {
        try {
            String response = Request.Get(KRAKEN_BASE_URL+"channels/"+channelId)
                    .addHeader("Accept", "application/vnd.twitchtv.v5+json")
                    .addHeader("Authorization", "OAuth " + token)
                    .addHeader("Client-ID", CLIENT_ID)
                    .execute()
                    .returnContent().asString();
            
            return (JSONObject) JSONValue.parse(response);
        } catch (IOException ignored) {
        } return null;
    }
    
    public static JSONObject changeStatus(String status, TwitchController tc) {
        try {
            String response = Request.Put(KRAKEN_BASE_URL+"channels/"+tc.getUser().getId()+"?channel[status]="+URLEncoder.encode(status, "UTF-8"))
                    .addHeader("Accept", "application/vnd.twitchtv.v5+json")
                    .addHeader("Authorization", "OAuth " + tc.getAuthToken())
                    .addHeader("Client-ID", CLIENT_ID)
                    .execute()
                    .returnContent().asString();
            
            return (JSONObject) JSONValue.parse(response);
        } catch (IOException ignored) {
        } return null;
    }
    
    public static void addStatusToCollection(Status s, TwitchController tc) {
        tc.getChannel().addStatus(s);
        saveStatusCollection(tc.getChannel());
        tc.getApp().updateChannel();
    }
    
    public static void removeStatusFromCollection(Status s, TwitchController tc) {
        tc.getChannel().removeStatus(s);
        saveStatusCollection(tc.getChannel());
        tc.getApp().updateChannel();
    }
    
    public static void saveStatusCollection(Channel c) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream("status"+c.getId()+".dat"));
            oos.writeObject(c.getStatusCollection());
        } catch (IOException ex) {
        }
    }
    
    public static List<Status> loadStatusCollection(Channel c) {
        try {
            ObjectInputStream ois = new ObjectInputStream( new FileInputStream("status"+c.getId()+".dat") );
            return (List<Status>) ois.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
        }
        return new ArrayList<>();
    }
    

}

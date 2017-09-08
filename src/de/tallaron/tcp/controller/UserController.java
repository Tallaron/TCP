package de.tallaron.tcp.controller;

import de.tallaron.tcp.entity.User;
import org.json.simple.JSONObject;
import static de.tallaron.tcp.Settings.*;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import org.json.simple.JSONValue;

public class UserController {
    
    
    
    public static User createObj(TwitchController tc) {
        JSONObject data = loadData( tc.getAuthToken() );
        User u = new User();
        u.setId( Integer.parseInt(data.get("_id").toString()) );
        u.setName( data.get("display_name").toString() );
        u.setEmail( data.get("email").toString() );
        u.setBio( data.get("bio").toString() );
        return u;
    }





    private static JSONObject loadData(String token) {
        try {
            String response = Request.Get(KRAKEN_BASE_URL+"user")
                    .addHeader("Accept", "application/vnd.twitchtv.v5+json")
                    .addHeader("Authorization", "OAuth " + token)
                    .addHeader("Client-ID", CLIENT_ID)
                    .execute()
                    .returnContent().asString();

            return (JSONObject) JSONValue.parse(response);
        } catch (IOException ignored) {
        } return null;
    }










    
    
}

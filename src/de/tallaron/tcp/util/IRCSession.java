
package de.tallaron.tcp.util;

import de.tallaron.tcp.Settings;
import de.tallaron.tcp.controller.TwitchController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javafx.application.Platform;

/**
 *
 * @author Administrator
 */
public class IRCSession implements Runnable {
    
    private TwitchController tc;

    public IRCSession(TwitchController tc) {
        this.tc = tc;
    }
    
    @Override
    public void run() {

        Socket socket;
        try {
            socket = new Socket(Settings.IRC_SERVER, Settings.IRC_PORT);
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));

            // Log on to the server.
            writer.write("PASS oauth:"+ tc.getAuthToken() +"\r\n");
            writer.write("NICK " + tc.getUser().getName().toLowerCase() + "\r\n");
            writer.flush();

            // Read lines from the server until it tells us we have connected.
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf("004") >= 0) {
                    // We are now logged in.
                    System.out.println("----- LOGGED IN -----");
                    break;
                } else if (line.indexOf("433") >= 0) {
                    System.out.println("Nickname is already in use.");
                    return;
                } else {
                    System.out.println(line.toString());
                }
            }

            // Join the channel.
            writer.write("JOIN #" + tc.getUser().getName().toLowerCase() + "\r\n");
//            writer.write("PRIVMSG #"+ tc.getUser().getName().toLowerCase() +" BotTest\r\nS");
            writer.flush();

            // Keep reading lines from the server.
            while ((line = reader.readLine()) != null) {
                if (line.toUpperCase().startsWith("PING ")) {
                    // We must respond to PINGs to avoid being disconnected.
//                    System.out.println("----- PONG ("+line.substring(6)+") -----");
                    writer.write("PONG " + line.substring(6) + "\r\n");
//                    writer.write("PRIVMSG " + CHANNEL + " :I got pinged!\r\n");
                    writer.flush();
                } else {
                    // Print the raw line received by the bot.
//                    System.out.println(line);
                    drawLine(line);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }





    }
    
    
    
    private void drawLine(final String line) {
        if(line.contains("PRIVMSG")) {
            String msgLine = line.split("PRIVMSG")[1];
            String user = msgLine.split(" :")[0].replace(" #", "");
            String msg = msgLine.split(" :")[1];
            String chatLine = "["+user+"]: "+msg;
            new Thread(() -> Platform.runLater( () -> tc.getApp().updateChat( chatLine ) )).start();
        }
    }
    
    
    

    public TwitchController getTc() {
        return tc;
    }

    public void setTc(TwitchController tc) {
        this.tc = tc;
    }
    
    
    
    
    
    
}

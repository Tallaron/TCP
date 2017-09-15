
package de.tallaron.tcp.util;

import de.tallaron.tcp.Settings;
import de.tallaron.tcp.controller.TwitchController;
import de.tallaron.tcp.ui.ChatLine;
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
    private Socket socket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private boolean connected = true;

    public IRCSession(TwitchController tc) {
        this.tc = tc;
        try {
            socket = new Socket(Settings.IRC_SERVER, Settings.IRC_PORT);
            writer = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
            reader = new BufferedReader( new InputStreamReader(socket.getInputStream(), "UTF-8"));
        } catch(IOException ignored) {
            
        }
    }
    
    @Override
    public void run() {
        try {
            // Log on to the server.
            sendLine("PASS oauth:"+ tc.getAuthToken() +"\r\n");
            sendLine("NICK " + tc.getUser().getName().toLowerCase() + "\r\n");
            sendLine("CAP REQ :twitch.tv/commands\r\n");
            // Read lines from the server until it tells us we have connected.
            if(!connect()) { return; }

            // Join the channel.
            sendLine("JOIN #" + tc.getUser().getName().toLowerCase() + "\r\n");

            // Keep reading lines from the server.
            listen();
        } catch(IOException ignored) {
        } finally {
            close();
        }
    }
    
    
    
    private boolean connect() throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            /*
             * if (line.indexOf("433") >= 0)
             * Error code for "nickname already in use"
             */
            if (line.contains("004")) {
                return true;
            }
        }
        return false;
    }
    
    public void diconnect() {
        close();
    }
    
    private void close() {
        try {
            if(socket != null)
                socket.close();
            if(reader != null)
                reader.close();
            if(writer != null)
                writer.close();
        } catch (IOException ignored) {
        }
    }
    
    private void sendLine(String line) throws IOException {
        writer.write(line);
        writer.flush();
    }
    
    public void sendMessage(String channel, String msg) throws IOException {
        if(!msg.substring(0, 1).equalsIgnoreCase("/")) {
            sendLine(String.format("PRIVMSG #%s :%s\r\n", channel, msg));
            drawLine(tc.getUser().getName(), msg);
        } else {
            sendLine(String.format("NAMES #%s\r\n", channel));
            sendLine(String.format("NAMES\r\n"));
        }
    }

    public void sendMessage(String msg) throws IOException {
        sendMessage(tc.getChannel().getName().toLowerCase(), msg);
    }
    
    
    private void listen() throws IOException {
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.toUpperCase().startsWith("PING ")) {
                // We must respond to PINGs to avoid being disconnected.
                sendLine("PONG " + line.substring(6) + "\r\n");
            } else {
                // Print the raw line received by the bot.
                System.out.println(line);
                drawLine(line);
            }
        }
    }
    
    
    private void drawLine(String line) {
        if(line.contains("PRIVMSG")) {
            String user = line.split("!", 2)[0].replace(":", "");
            String msg = line.split(" :", 2)[1];
            new Thread(() -> Platform.runLater( () -> tc.getApp().updateChat( ChatLine.of(user, msg) ) )).start();
        }
    }
    
    private void drawLine(String user, String msg) {
        new Thread(() -> Platform.runLater( () -> tc.getApp().updateChat( ChatLine.of(user, msg) ) )).start();
    }
    
    

    public TwitchController getTc() {
        return tc;
    }

    public void setTc(TwitchController tc) {
        this.tc = tc;
    }
    
    
    
    
    
    
}

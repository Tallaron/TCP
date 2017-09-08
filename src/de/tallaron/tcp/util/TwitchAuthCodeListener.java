package de.tallaron.tcp.util;

import static de.tallaron.tcp.Settings.ERROR_PAGE;
import static de.tallaron.tcp.Settings.SUCCESS_PAGE;
import de.tallaron.tcp.controller.TwitchController;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Platform;

public class TwitchAuthCodeListener implements Runnable {
    
    private TwitchController tc;
    private long start;

    public TwitchAuthCodeListener(TwitchController tc) {
        this.tc = tc;
    }

    @Override
    public void run() {
        ServerSocket socket = null;
        Socket con = null;
        try {
            socket = new ServerSocket(27779, 0, InetAddress.getByName("127.0.0.1"));
            while (!(tc.getAuthCode() != null || tc.isError())) {
                con = socket.accept();
                BufferedReader reader = getReader(con);
                BufferedWriter writer = getWriter(con);
                
                // READ INPUT STREAM
                while (!checkLine(reader.readLine())) {} 

                // WRITE OUTPUT STREAM AS HTTP RESPONSE
                if (tc.getAuthCode()!= null) {
                    Platform.runLater( () -> tc.receiveAuthToken() ); //call a method in main (JavaFX) thread
                    sendSuccessHeader(writer);
                } else {
                    Platform.runLater( () -> tc.getApp().authDone() );
                    sendErrorHeader(writer);
                }

                //CLOSE ALL STREAMS AND SOCKET
                reader.close();
                writer.close();
                con.close();
            }
            socket.close();
        } catch (UnknownHostException ignored) {
        } catch (IOException ignored) {
        }
        
    }
    
    private BufferedReader getReader(Socket s) throws IOException {
        return new BufferedReader(new InputStreamReader(s.getInputStream(), "UTF-8"));
    }

    private BufferedWriter getWriter(Socket s) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
    }

    private boolean checkLine(String line) {
        if (line.contains("code=")) {
            tc.setAuthCode(line.split("code=", 2)[1].split("&", 2)[0]);
            return true;
        } else if (line.contains("error=")) {
            tc.setError(true);
            return true;
        } return false;
    }

    private void sendSuccessHeader(BufferedWriter w) throws IOException {
        w.write("HTTP/1.1 200 Ok\r\n");
        w.write("Refresh: 0; url="+SUCCESS_PAGE+"\r\n"); //TODO: use getter/setter
        w.flush();
    }

    private void sendErrorHeader(BufferedWriter w) throws IOException {
        w.write("HTTP/1.1 200 Ok\r\n");
        w.write("Refresh: 0; url="+ERROR_PAGE+"\r\n"); //TODO: use getter/setter
        w.flush();
    }

}

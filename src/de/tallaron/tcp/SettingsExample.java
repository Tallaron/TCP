package de.tallaron.tcp;

/**
 * 
 * @author Tallaron
 * This class just represents the settings class which includes some private
 * credentials. For usage, rename this class to Settings and fill out the missing fields.
 */

abstract public class SettingsExample {

    public static final String KRAKEN_BASE_URL = "https://api.twitch.tv/kraken/";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String REDIRECT_URL = "http://127.0.0.1:27779";
    public static final String SUCCESS_PAGE = "http://127.0.0.1/test/success.php";
    public static final String ERROR_PAGE = "http://127.0.0.1/test/error.php";
    public static final String IRC_SERVER = "irc.chat.twitch.tv";
    
    public static final boolean FORCE_VERIFY = false;
    
    public static final long DEFAULT_HTTP_TIMEOUT = 5000L;
    public static final int IRC_PORT = 6667;
    
}

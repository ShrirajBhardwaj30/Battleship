package player;

public class PlayerConfigurationException extends Exception {
    public PlayerConfigurationException(String message) {
        super(message);
    }

    public PlayerConfigurationException(Exception e) {
        super(e);
    }
}

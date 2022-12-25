package game;

public class GameBuildException extends Exception {
    public GameBuildException(String e) {
        super(e);
    }

    public GameBuildException(Exception e) {
        super(e);
    }
}

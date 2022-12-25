package player;

public class InvalidPlayerInputException extends Exception {
    public InvalidPlayerInputException(Exception e) {
        super(e.getMessage());
    }
}

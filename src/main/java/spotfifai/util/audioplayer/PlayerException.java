package spotfifai.util.audioplayer;

public class PlayerException extends Exception {
    public PlayerException(Throwable cause) {
        super(cause);
    }
    
    public PlayerException(String message) {
        super(message);
    }
}
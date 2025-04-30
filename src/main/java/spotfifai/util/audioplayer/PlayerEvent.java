package spotfifai.util.audioplayer;

public class PlayerEvent {
    public static final int OPENING = 0;
    public static final int OPENED = 1;
    public static final int PLAYING = 2;
    public static final int STOPPED = 3;
    public static final int PAUSED = 4;
    public static final int RESUMED = 5;
    public static final int SEEKING = 6;
    public static final int SEEKED = 7;
    public static final int EOM = 8;
    public static final int PAN = 9;
    public static final int GAIN = 10;
    
    private int code;
    private int position;
    private double value;
    private Object description;
    
    public PlayerEvent(int code, int position, double value, Object description) {
        this.code = code;
        this.position = position;
        this.value = value;
        this.description = description;
    }
    
    public int getCode() {
        return code;
    }
    
    public int getPosition() {
        return position;
    }
    
    public double getValue() {
        return value;
    }
    
    public Object getDescription() {
        return description;
    }
}
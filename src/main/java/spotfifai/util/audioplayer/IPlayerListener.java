package spotfifai.util.audioplayer;

import java.util.Map;

public interface IPlayerListener {
    void opening(Object dataSource);
    void opened(Object dataSource, Map<String, Object> properties);
    void progress(int bytesRead, long microseconds, byte[] pcmData, Map<String, Object> properties);
    void playing();
    void paused();
    void resumed();
    void stopped();
    void seeking();
    void seeked();
    void endOfMedia();
    void pan(double pan);
    void gain(double gain);
}
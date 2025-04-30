package spotfifai.util.audioplayer;

import java.io.*;
import java.net.URL;
import java.util.*;
import javax.sound.sampled.*;

public class AudioPlayer implements Runnable {
    public static final int UNKNOWN = -1;
    public static final int PLAYING = 0;
    public static final int PAUSED = 1;
    public static final int STOPPED = 2;
    public static final int OPENED = 3;
    public static final int SEEKING = 4;
    
    private static final int BUFFER_SIZE = 4000 * 40;
    private static final int SKIP_INACCURACY_SIZE = 1200;
    
    private Thread playerThread = null;
    private Object dataSource;
    private AudioInputStream audioInputStream;
    private AudioInputStream encodedAudioInputStream;
    private AudioFileFormat audioFileFormat;
    private SourceDataLine line;
    
    private FloatControl gainControl;
    private FloatControl panControl;
    private String mixerName = null;
    
    private int status = UNKNOWN;
    private int encodedLength = -1;
    private int lineBufferSize = -1;
    private int lineCurrentBufferSize = -1;
    private long threadSleep = -1;
    
    private List<PlayerListener> listeners;
    private Map<String, Object> emptyProperties = new HashMap<>();
    
    public AudioPlayer() {
        listeners = new ArrayList<>();
        reset();
    }
    
    protected void reset() {
        status = UNKNOWN;
        if (audioInputStream != null) {
            synchronized (audioInputStream) {
                closeStream();
            }
        }
        audioInputStream = null;
        audioFileFormat = null;
        encodedAudioInputStream = null;
        encodedLength = -1;
        if (line != null) {
            line.stop();
            line.close();
            line = null;
        }
        gainControl = null;
        panControl = null;
    }
    
    public void addPlayerListener(PlayerListener listener) {
        listeners.add(listener);
    }
    
    public List<PlayerListener> getListeners() {
        return listeners;
    }
    
    public void removePlayerListener(PlayerListener listener) {
        listeners.remove(listener);
    }
    
    public void setLineBufferSize(int size) {
        lineBufferSize = size;
    }
    
    public int getLineBufferSize() {
        return lineBufferSize;
    }
    
    public int getLineCurrentBufferSize() {
        return lineCurrentBufferSize;
    }
    
    public void setSleepTime(long time) {
        threadSleep = time;
    }
    
    public long getSleepTime() {
        return threadSleep;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void open(File file) throws PlayerException {
        if (file != null) {
            dataSource = file;
            initAudioInputStream();
        }
    }
    
    public void open(URL url) throws PlayerException {
        if (url != null) {
            dataSource = url;
            initAudioInputStream();
        }
    }
    
    public void open(InputStream inputStream) throws PlayerException {
        if (inputStream != null) {
            dataSource = inputStream;
            initAudioInputStream();
        }
    }
    
    protected void initAudioInputStream() throws PlayerException {
        try {
            reset();
            notifyEvent(PlayerEvent.OPENING, getEncodedStreamPosition(), -1, dataSource);
            
            if (dataSource instanceof URL) {
                initAudioInputStream((URL) dataSource);
            } else if (dataSource instanceof File) {
                initAudioInputStream((File) dataSource);
            } else if (dataSource instanceof InputStream) {
                initAudioInputStream((InputStream) dataSource);
            }
            
            createLine();
            Map<String, Object> properties = new HashMap<>();
            
            if (audioFileFormat.getByteLength() > 0) {
                properties.put("audio.length.bytes", audioFileFormat.getByteLength());
            }
            if (audioFileFormat.getFrameLength() > 0) {
                properties.put("audio.length.frames", audioFileFormat.getFrameLength());
            }
            if (audioFileFormat.getType() != null) {
                properties.put("audio.type", audioFileFormat.getType().toString());
            }
            
            AudioFormat audioFormat = audioFileFormat.getFormat();
            if (audioFormat.getFrameRate() > 0) {
                properties.put("audio.framerate.fps", audioFormat.getFrameRate());
            }
            if (audioFormat.getFrameSize() > 0) {
                properties.put("audio.framesize.bytes", audioFormat.getFrameSize());
            }
            if (audioFormat.getSampleRate() > 0) {
                properties.put("audio.samplerate.hz", audioFormat.getSampleRate());
            }
            if (audioFormat.getSampleSizeInBits() > 0) {
                properties.put("audio.samplesize.bits", audioFormat.getSampleSizeInBits());
            }
            if (audioFormat.getChannels() > 0) {
                properties.put("audio.channels", audioFormat.getChannels());
            }
            
            properties.put("audioplayer.sourcedataline", line);
            
            for (PlayerListener listener : listeners) {
                listener.opened(dataSource, properties);
            }
            
            status = OPENED;
            notifyEvent(PlayerEvent.OPENED, getEncodedStreamPosition(), -1, null);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            throw new PlayerException(e);
        }
    }
    
    protected void initAudioInputStream(File file) throws UnsupportedAudioFileException, IOException {
        audioInputStream = AudioSystem.getAudioInputStream(file);
        audioFileFormat = AudioSystem.getAudioFileFormat(file);
    }
    
    protected void initAudioInputStream(URL url) throws UnsupportedAudioFileException, IOException {
        audioInputStream = AudioSystem.getAudioInputStream(url);
        audioFileFormat = AudioSystem.getAudioFileFormat(url);
    }
    
    protected void initAudioInputStream(InputStream inputStream) throws UnsupportedAudioFileException, IOException {
        audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        audioFileFormat = AudioSystem.getAudioFileFormat(inputStream);
    }
    
    protected void initLine() throws LineUnavailableException {
        if (line == null) {
            createLine();
        }
        if (!line.isOpen()) {
            openLine();
        } else {
            AudioFormat lineAudioFormat = line.getFormat();
            AudioFormat audioInputStreamFormat = audioInputStream == null ? null : audioInputStream.getFormat();
            if (!lineAudioFormat.equals(audioInputStreamFormat)) {
                line.close();
                openLine();
            }
        }
    }
    
    protected void createLine() throws LineUnavailableException {
        if (line == null) {
            final AudioFormat sourceFormat = audioInputStream.getFormat();
            int sampleSizeInBits = sourceFormat.getSampleSizeInBits();
            
            if (sampleSizeInBits <= 0) {
                sampleSizeInBits = 16;
            }
            if ((sourceFormat.getEncoding() == AudioFormat.Encoding.ULAW) || 
                (sourceFormat.getEncoding() == AudioFormat.Encoding.ALAW)) {
                sampleSizeInBits = 16;
            }
            if (sampleSizeInBits != 8) {
                sampleSizeInBits = 16;
            }
            
            AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                sourceFormat.getSampleRate(),
                sampleSizeInBits,
                sourceFormat.getChannels(),
                sourceFormat.getChannels() * (sampleSizeInBits / 8),
                sourceFormat.getSampleRate(),
                false
            );
            
            encodedAudioInputStream = audioInputStream;
            try {
                encodedLength = encodedAudioInputStream.available();
            } catch (IOException e) {
                // Ignore
            }
            
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
            AudioFormat audioFormat = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
            
            Mixer mixer = getMixer(mixerName);
            if (mixer != null) {
                line = (SourceDataLine) mixer.getLine(info);
            } else {
                line = (SourceDataLine) AudioSystem.getLine(info);
                mixerName = null;
            }
        }
    }
    
    public void setMute(boolean mute) {
        BooleanControl c = (BooleanControl) findControl(BooleanControl.Type.MUTE, line.getControls());
        if (c != null) {
            c.setValue(mute);
        }
    }
    
    public void toggleMute() {
        BooleanControl c = (BooleanControl) findControl(BooleanControl.Type.MUTE, line.getControls());
        if (c != null) {
            c.setValue(!c.getValue());
        }
    }
    
    private static Control findControl(Control.Type type, Control[] controls) {
        if (controls == null || controls.length == 0) {
            return null;
        }
        
        for (Control control : controls) {
            if (control.getType().equals(type)) {
                return control;
            }
            if (control instanceof CompoundControl) {
                CompoundControl compoundControl = (CompoundControl) control;
                Control member = findControl(type, compoundControl.getMemberControls());
                if (member != null) {
                    return member;
                }
            }
        }
        return null;
    }
    
    protected void openLine() throws LineUnavailableException {
        if (line != null) {
            AudioFormat audioFormat = audioInputStream.getFormat();
            int bufferSize = lineBufferSize;
            
            if (bufferSize <= 0) {
                bufferSize = line.getBufferSize();
            }
            
            lineCurrentBufferSize = bufferSize;
            line.open(audioFormat, bufferSize);
            
            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            }
            
            if (line.isControlSupported(FloatControl.Type.PAN)) {
                panControl = (FloatControl) line.getControl(FloatControl.Type.PAN);
            }
        }
    }
    
    protected void stopPlayback() {
        if ((status == PLAYING) || (status == PAUSED)) {
            if (line != null) {
                line.flush();
                line.stop();
            }
            status = STOPPED;
            notifyEvent(PlayerEvent.STOPPED, getEncodedStreamPosition(), -1, null);
            synchronized (audioInputStream) {
                closeStream();
            }
        }
    }
    
    protected void pausePlayback() {
        if (line != null) {
            if (status == PLAYING) {
                line.flush();
                line.stop();
                status = PAUSED;
                notifyEvent(PlayerEvent.PAUSED, getEncodedStreamPosition(), -1, null);
            }
        }
    }
    
    protected void resumePlayback() {
        if (line != null) {
            if (status == PAUSED) {
                line.start();
                status = PLAYING;
                notifyEvent(PlayerEvent.RESUMED, getEncodedStreamPosition(), -1, null);
            }
        }
    }
    
    protected void startPlayback() throws PlayerException {
        if (status == STOPPED) {
            initAudioInputStream();
        }
        
        if (status == OPENED) {
            if (!(playerThread == null || !playerThread.isAlive())) {
                int cnt = 0;
                while (status != OPENED) {
                    try {
                        if (playerThread != null) {
                            cnt++;
                            Thread.sleep(1000);
                            if (cnt > 2) {
                                playerThread.interrupt();
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new PlayerException(e);
                    }
                }
            }
            
            try {
                initLine();
            } catch (LineUnavailableException e) {
                throw new PlayerException(e);
            }
            
            playerThread = new Thread(this, "AudioPlayer");
            playerThread.start();
            
            if (line != null) {
                line.start();
                status = PLAYING;
                notifyEvent(PlayerEvent.PLAYING, getEncodedStreamPosition(), -1, null);
            }
        }
    }
    
    public SourceDataLine getLine() {
        return line;
    }
    
    @Override
    public void run() {
        int bytesRead = 1;
        byte[] buffer = new byte[BUFFER_SIZE];
        
        synchronized (audioInputStream) {
            while ((bytesRead != -1) && (status != STOPPED) && (status != SEEKING) && (status != UNKNOWN)) {
                if (status == PLAYING) {
                    try {
                        bytesRead = audioInputStream.read(buffer, 0, buffer.length);
                        
                        if (bytesRead >= 0) {
                            int encodedBytes = getEncodedStreamPosition();
                            
                            if (line.available() >= line.getBufferSize()) {
                                // Buffer underrun occurred
                            }
                            
                            line.write(buffer, 0, bytesRead);
                            
                            for (PlayerListener listener : listeners) {
                                listener.progress(encodedBytes, line.getMicrosecondPosition(), buffer, emptyProperties);
                            }
                        }
                    } catch (IOException e) {
                        status = STOPPED;
                        notifyEvent(PlayerEvent.STOPPED, getEncodedStreamPosition(), -1, null);
                    }
                    
                    if (threadSleep > 0) {
                        try {
                            Thread.sleep(threadSleep);
                        } catch (InterruptedException e) {
                            // Ignore
                        }
                    }
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Ignore
                    }
                }
            }
            
            if (line != null) {
                line.drain();
                line.stop();
                line.close();
                line = null;
            }
            
            if (bytesRead == -1) {
                notifyEvent(PlayerEvent.EOM, getEncodedStreamPosition(), -1, null);
            }
            
            closeStream();
        }
        
        status = STOPPED;
        notifyEvent(PlayerEvent.STOPPED, getEncodedStreamPosition(), -1, null);
    }
    
    protected long skipBytes(long bytes) throws PlayerException {
        long totalSkipped = 0;
        if (dataSource instanceof File) {
            int previousStatus = status;
            status = SEEKING;
            long skipped = 0;
            
            try {
                synchronized (audioInputStream) {
                    notifyEvent(PlayerEvent.SEEKING, getEncodedStreamPosition(), -1, null);
                    initAudioInputStream();
                    
                    if (audioInputStream != null) {
                        while (totalSkipped < (bytes - SKIP_INACCURACY_SIZE)) {
                            skipped = audioInputStream.skip(bytes - totalSkipped);
                            if (skipped == 0) {
                                break;
                            }
                            totalSkipped = totalSkipped + skipped;
                            if (totalSkipped == -1) {
                                throw new PlayerException("Skip not supported");
                            }
                        }
                    }
                }
                
                notifyEvent(PlayerEvent.SEEKED, getEncodedStreamPosition(), -1, null);
                status = OPENED;
                
                if (previousStatus == PLAYING) {
                    startPlayback();
                } else if (previousStatus == PAUSED) {
                    startPlayback();
                    pausePlayback();
                }
            } catch (IOException e) {
                throw new PlayerException(e);
            }
        }
        return totalSkipped;
    }
    
    protected void notifyEvent(int code, int position, double value, Object description) {
        PlayerEvent event = new PlayerEvent(code, position, value, description);
        
        for (PlayerListener listener : listeners) {
            switch (code) {
                case PlayerEvent.OPENED:
                    listener.opened(description, new HashMap<>());
                    break;
                case PlayerEvent.OPENING:
                    listener.opening(description);
                    break;
                case PlayerEvent.PLAYING:
                    listener.playing();
                    break;
                case PlayerEvent.STOPPED:
                    listener.stopped();
                    break;
                case PlayerEvent.PAUSED:
                    listener.paused();
                    break;
                case PlayerEvent.RESUMED:
                    listener.resumed();
                    break;
                case PlayerEvent.SEEKING:
                    listener.seeking();
                    break;
                case PlayerEvent.SEEKED:
                    listener.seeked();
                    break;
                case PlayerEvent.EOM:
                    listener.endOfMedia();
                    break;
                case PlayerEvent.PAN:
                    listener.pan(value);
                    break;
                case PlayerEvent.GAIN:
                    listener.gain(value);
                    break;
            }
        }
    }
    
    protected int getEncodedStreamPosition() {
        int encodedBytes = -1;
        if (dataSource instanceof File) {
            try {
                if (encodedAudioInputStream != null) {
                    encodedBytes = encodedLength - encodedAudioInputStream.available();
                }
            } catch (IOException e) {
                // Ignore
            }
        }
        return encodedBytes;
    }
    
    protected void closeStream() {
        try {
            if (audioInputStream != null) {
                audioInputStream.close();
            }
        } catch (IOException e) {
            // Ignore
        }
    }
    
    public boolean hasGainControl() {
        if (gainControl == null) {
            if ((line != null) && (line.isControlSupported(FloatControl.Type.MASTER_GAIN))) {
                gainControl = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
            }
        }
        return gainControl != null;
    }
    
    public float getGainValue() {
        if (hasGainControl()) {
            return gainControl.getValue();
        } else {
            return 0.0F;
        }
    }
    
    public float getMaximumGain() {
        if (hasGainControl()) {
            return gainControl.getMaximum();
        } else {
            return 0.0F;
        }
    }
    
    public float getMinimumGain() {
        if (hasGainControl()) {
            return gainControl.getMinimum();
        } else {
            return 0.0F;
        }
    }
    
    public boolean hasPanControl() {
        if (panControl == null) {
            if ((line != null) && (line.isControlSupported(FloatControl.Type.PAN))) {
                panControl = (FloatControl) line.getControl(FloatControl.Type.PAN);
            }
        }
        return panControl != null;
    }
    
    public float getPrecision() {
        if (hasPanControl()) {
            return panControl.getPrecision();
        } else {
            return 0.0F;
        }
    }
    
    public float getPan() {
        if (hasPanControl()) {
            return panControl.getValue();
        } else {
            return 0.0F;
        }
    }
    
    public long seek(long bytes) throws PlayerException {
        return skipBytes(bytes);
    }
    
    public void play() throws PlayerException {
        startPlayback();
    }
    
    public void stop() throws PlayerException {
        stopPlayback();
    }
    
    public void pause() throws PlayerException {
        pausePlayback();
    }
    
    public void resume() throws PlayerException {
        resumePlayback();
    }
    
    public void setPan(double pan) throws PlayerException {
        if (hasPanControl()) {
            panControl.setValue((float) pan);
            notifyEvent(PlayerEvent.PAN, getEncodedStreamPosition(), pan, null);
        } else {
            throw new PlayerException("Pan control not supported");
        }
    }
    
    public void setGain(double gain) throws PlayerException {
        if (hasGainControl()) {
            double minGainDB = getMinimumGain();
            double ampGainDB = ((10.0f / 20.0f) * getMaximumGain()) - getMinimumGain();
            double cste = Math.log(10.0) / 20;
            double valueDB = minGainDB + (1 / cste) * Math.log(1 + (Math.exp(cste * ampGainDB) - 1) * gain);
            gainControl.setValue((float) valueDB);
            notifyEvent(PlayerEvent.GAIN, getEncodedStreamPosition(), gain, null);
        } else {
            throw new PlayerException("Gain control not supported");
        }
    }
    
    public List<String> getMixers() {
        ArrayList<String> mixers = new ArrayList<>();
        Mixer.Info[] mInfos = AudioSystem.getMixerInfo();
        
        if (mInfos != null) {
            for (int i = 0; i < mInfos.length; i++) {
                Line.Info lineInfo = new Line.Info(SourceDataLine.class);
                Mixer mixer = AudioSystem.getMixer(mInfos[i]);
                if (mixer.isLineSupported(lineInfo)) {
                    mixers.add(mInfos[i].getName());
                }
            }
        }
        return mixers;
    }
    
    public Mixer getMixer(String name) {
        Mixer mixer = null;
        if (name != null) {
            Mixer.Info[] mInfos = AudioSystem.getMixerInfo();
            if (mInfos != null) {
                for (int i = 0; i < mInfos.length; i++) {
                    if (mInfos[i].getName().equals(name)) {
                        mixer = AudioSystem.getMixer(mInfos[i]);
                        break;
                    }
                }
            }
        }
        return mixer;
    }
    
    public String getMixerName() {
        return mixerName;
    }
    
    public void setMixerName(String name) {
        mixerName = name;
    }
}
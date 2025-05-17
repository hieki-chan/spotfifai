package spotfifai.util.audioplayer;

import java.io.File;
import java.util.Map;
import javax.swing.JOptionPane;

public class AudioPlayerDemo
{

    public static void main(String[] args)
    {

        try
        {
            File file = new File("S:\\Java\\spotfifai\\src\\main\\java\\resources\\A little girl.wav");
            AudioPlayer player = new AudioPlayer();

            // Add a basic listener
            player.addPlayerListener(new SimplePlayerListener());

            // Open audio file
            player.open(file);

            // Start playback
            player.seek(9000000);
            player.play();

            // Wait for playback to complete
//            while (player.getStatus() == AudioPlayer.PLAYING) {
//                Thread.sleep(1000);
//            }
            Runtime.getRuntime().addShutdownHook(new Thread(() ->
            {
                player.close();
                System.out.println("AudioPlayer closed via Shutdown Hook");

            }, "ShutdownHook-AudioPlayer"));

            JOptionPane.showMessageDialog(null, player);
            player.close();

        } catch (PlayerException e)
        {
            e.printStackTrace();
        }
    }

    public static class SimplePlayerListener implements IPlayerListener
    {

        @Override
        public void opening(Object dataSource)
        {
            //System.out.println("Opening: " + dataSource);
        }

        @Override
        public void opened(Object dataSource, Map<String, Object> properties)
        {
            System.out.println("Opened: " + dataSource);
            System.out.println("Format properties: " + properties);
        }

        @Override
        public void progress(int bytesRead, long microseconds, byte[] pcmData, Map<String, Object> properties)
        {
            // Uncomment to see progress (will spam console)
            //System.out.println("Progress: " + bytesRead + " bytes, " + (microseconds / 1000) + " ms");
        }

        @Override
        public void playing()
        {
            System.out.println("Playing started");
        }

        @Override
        public void paused()
        {
            System.out.println("Playback paused");
        }

        @Override
        public void resumed()
        {
            System.out.println("Playback resumed");
        }

        @Override
        public void stopped()
        {
            System.out.println("Playback stopped");
        }

        @Override
        public void seeking()
        {
            System.out.println("Seeking");
        }

        @Override
        public void seeked()
        {
            System.out.println("Seek complete");
        }

        @Override
        public void endOfMedia()
        {
            System.out.println("End of media reached");
        }

        @Override
        public void pan(double pan)
        {
            System.out.println("Pan set to: " + pan);
        }

        @Override
        public void gain(double gain)
        {
            System.out.println("Gain set to: " + gain);
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.ui.MainFrame;
import spotfifai.models.Song;
import spotfifai.util.audioplayer.AudioPlayer;
import spotfifai.util.audioplayer.PlayerException;
import spotfifai.util.located.IService;
import spotfifai.util.audioplayer.IPlayerListener;

/**
 *
 * @author admin
 */
public class MusicPlayerController implements IPlayerListener, IService
{

    AudioPlayer audioPlayer;
    Song currentSong;
    File tempFile;
    boolean loop;

    IMusicListenter musicListener;

    public MusicPlayerController(AudioPlayer audioPlayer)
    {
        this.audioPlayer = audioPlayer;
        // Add a basic listener
        audioPlayer.addPlayerListener(this);
        try
        {
            // Open audio file
            audioPlayer.open("S:\\Java\\spotfifai\\src\\main\\resources\\resources\\A Thousand Years.wav");
            audioPlayer.play();
        } catch (PlayerException ex)
        {
            Logger.getLogger(MusicPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setListener(IMusicListenter musicListener)
    {
        this.musicListener = musicListener;
    }

    public float getAudioLength()
    {
        return audioPlayer.getMaxMicrosecondPosition();
    }

    public void play(Song song)
    {
        if (song == currentSong)
        {
            return;
        }

        if (tempFile != null)
        {
            tempFile.delete();
        }
        try
        {
            byte[] audioBytes = song.getAudioData();

            tempFile = File.createTempFile("music", ".wav");

            try (FileOutputStream fos = new FileOutputStream(tempFile))
            {
                fos.write(audioBytes);
            }

            audioPlayer.open(tempFile);
            audioPlayer.play();
            currentSong = song;

        } catch (IOException ex)
        {
            Logger.getLogger(MusicPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PlayerException ex)
        {
            Logger.getLogger(MusicPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resume()
    {
        try
        {
            audioPlayer.resume();
            //audioPlayer.play();
        } catch (PlayerException ex)
        {
            Logger.getLogger(MusicPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pause()
    {
        try
        {
            audioPlayer.pause();
        } catch (PlayerException ex)
        {
            Logger.getLogger(MusicPlayerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void seek(float ratio)
    {
        long audioMicrosecondLength = audioPlayer.getMaxMicrosecondPosition();
        long microsecondPosition = (long) (ratio * audioMicrosecondLength);
        //System.out.println(microsecondPosition);
        try
        {

            audioPlayer.seek(microsecondPosition);
            //player.play();
        } catch (PlayerException ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setLoop(boolean state)
    {
        loop = state;
    }

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
        if (properties.size() == 0)
        {
            return;
        }
        if (musicListener != null && currentSong != null)
        {
            float duration = (Float) properties.get("audio.duration.seconds");

            musicListener.onOpen(currentSong, duration);
        }
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
        if (loop)
        {
            try
            {
                audioPlayer.seek(0);
                audioPlayer.play();
            } catch (PlayerException ex)
            {
                Logger.getLogger(MusicPlayerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
//            if (tempFile != null)
//            {
//                tempFile.delete();
//            }
        }
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

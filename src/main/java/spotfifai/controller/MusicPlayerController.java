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
import javax.swing.Timer;
import spotfifai.dao.SongDAO;
import spotfifai.models.Playlist;
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

    SongDAO songDAO;
    AudioPlayer audioPlayer;
    Playlist currentPlaylist;
    int currentIndex;
    Song currentSong;
    File tempFile;
    boolean loop;
    boolean seeking;
    boolean shouldNext;

    IMusicListenter musicListener;

    public MusicPlayerController(AudioPlayer audioPlayer, SongDAO songDAO)
    {
        this.audioPlayer = audioPlayer;
        this.songDAO = songDAO;
        // Add a basic listener
        audioPlayer.addPlayerListener(this);
    }

    public void setListener(IMusicListenter musicListener)
    {
        this.musicListener = musicListener;
    }

    public float getAudioLength()
    {
        return audioPlayer.getMaxMicrosecondPosition();
    }

    public boolean isPlaying()
    {
        return audioPlayer.getStatus() == AudioPlayer.PLAYING;
    }

    public void playAPlaylist(Playlist playlist)
    {
        currentIndex = -1;
        //currentPlaylist = null;
        //audioPlayer.stop();
        currentPlaylist = playlist;
        nextSong();
    }

    public void nextSong()
    {
        if (currentPlaylist == null)
        {
            return;
        }

        currentIndex++;
        playCurrentSongInPlaylist();
    }

    public void previousSong()
    {
        if (currentPlaylist == null)
        {
            return;
        }

        currentIndex--;
        playCurrentSongInPlaylist();
    }

    private void playCurrentSongInPlaylist()
    {
        if (currentPlaylist == null)
        {
            return;
        }
        System.out.println(currentIndex);
        var playlistSongs = currentPlaylist.getPlaylistDetails();
        if (currentIndex >= playlistSongs.size() || currentIndex < 0)
        {
            currentIndex = playlistSongs.size() - 1;
            return;
        }

        String songId = playlistSongs.get(currentIndex).getSongId();
        Song song = songDAO.getSongWithAudioData(songId);
        System.out.println(song);

        if (song != null)
        {
            shouldNext = false;
            playImmediately(song);
            shouldNext = true;
        }
    }

    public void playDelayed(Song song)
    {
        Timer timer = new Timer(100, e ->
        {
            playImmediately(song);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void playImmediately(Song song)
    {
        if (song == currentSong)
        {
            return;
        }

        if (tempFile != null)
        {
            System.out.println("temp file deleted");
            audioPlayer.close();
            tempFile.delete();
        }
        try
        {
            currentSong = song;

            if (currentSong.getAudioData() == null)
            {
                currentSong.setAudioData(songDAO.getSongWithAudioData(currentSong.getSongId()).getAudioData());
            } else
            {
                //return;
            }

            //System.out.println(currentSong.getAudioData());
            byte[] audioBytes = song.getAudioData();

            tempFile = File.createTempFile("music", ".wav");
            tempFile.deleteOnExit();

            try (FileOutputStream fos = new FileOutputStream(tempFile))
            {
                fos.write(audioBytes);
            }

            audioPlayer.open(tempFile);
            audioPlayer.play();

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
            if (audioPlayer.getStatus() == AudioPlayer.PAUSED)
            {
                audioPlayer.resume();
            } else if (audioPlayer.getStatus() == AudioPlayer.STOPPED)
            {
                audioPlayer.seek(0);
                audioPlayer.play();
            }
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
            seeking = true;
            audioPlayer.seek(microsecondPosition);
            if (!isPlaying())
            {
                audioPlayer.play();
            }
            //player.playDelayed();
        } catch (PlayerException ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setLoop(boolean state)
    {
        loop = state;
    }

    public void dispose()
    {
        if(tempFile != null)
        {
            tempFile.delete();
            audioPlayer.close();
        }
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
        if (properties.isEmpty())
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
        // System.out.println("Progress: " + bytesRead + " bytes, " + (microseconds / 1000) + " ms");
        // System.out.println(properties);
        if (properties.isEmpty())
        {
            return;
        }

        float progressRatio = bytesRead / (float) audioPlayer.getMaxMicrosecondPosition();
        float progressInSeconds = progressRatio * (Float) properties.get("audio.duration.seconds");

        if (musicListener != null && !seeking)
        {
            musicListener.onProgress(progressRatio, progressInSeconds);
        }
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

    }

    @Override
    public void seeking()
    {
        seeking = true;
        System.out.println("Seeking");
    }

    @Override
    public void seeked()
    {
        seeking = false;
        System.out.println("Seek complete");
    }

    @Override
    public void endOfMedia()
    {
        System.out.println("End of media reached");
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
//            error  but i dont know why
//            if (tempFile != null)
//            {
//                tempFile.delete();
//            }

        }
        nextSong();
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

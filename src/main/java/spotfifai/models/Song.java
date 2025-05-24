/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.models;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author admin
 */
public class Song
{

    private String songId;
    private String title;
    private String description;
    private String artistId;
    private byte[] audioData;

    public Song(String songId, String title, String description, byte[] audioData)
    {
        this.songId = songId;
        this.title = title;
        this.description = description;
        this.audioData = audioData;
    }

    public Song(String title, String description, byte[] audioData)
    {
        String uid = UUID.randomUUID().toString().substring(0, 20);
        this(uid, title, description, audioData);
    }

    public Song()
    {
        this("New song...", "description...", null);
    }

    public Song(Song other)
    {
        this(other.songId, other.title, other.description, other.audioData);
    }

    public String getSongId()
    {
        return songId;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public int getArtistId()
    {
        return artistId.hashCode();
    }

    public byte[] getAudioData()
    {
        return audioData;
    }

    public void setSongId(String songId)
    {
        this.songId = songId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setAudioData(byte[] audioData)
    {
        this.audioData = audioData;
    }

    @Override
    public String toString()
    {
        return "Song{" + "id=" + songId + ", name=" + title + ", description=" + description + '}';
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.songId);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Song other = (Song) obj;
        return this.songId == other.songId;
    }
}

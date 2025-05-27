/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Playlist implements Comparable<Playlist>
{

    private int playlistId;
    private String title;
    private List<PlaylistDetail> playlistDetails;

    public Playlist(int playlistId, String title, List<PlaylistDetail> songIds)
    {
        this.playlistId = playlistId;
        this.title = title;
        this.playlistDetails = songIds;
    }

    public Playlist(int playlistId, String title)
    {
        this(playlistId, title, new ArrayList<>());
    }
    
    public Playlist(String title)
    {
        this(-1, title, new ArrayList<>());
    }

    public int getPlaylistId()
    {
        return playlistId;
    }

    public void setPlaylistId(int playlistId)
    {
        this.playlistId = playlistId;
    }

    public String getTitle()
    {
        return title;
    }

    public List<PlaylistDetail> getPlaylistDetails()
    {
        return playlistDetails;
    }

    public void setPlaylistDetails(List<PlaylistDetail> songIds)
    {
        this.playlistDetails = songIds;
    }

    @Override
    public String toString()
    {
        return "Playlist{" + "playlistId=" + playlistId + ", title=" + title + ", playlistDetails=" + playlistDetails + '}';
    }

    @Override
    public int hashCode()
    { 
        return this.playlistId;
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
        final Playlist other = (Playlist) obj;
        return this.playlistId == other.playlistId;
    }

    @Override
    public int compareTo(Playlist o)
    {
        return this.getTitle().compareTo(o.getTitle());
//        int comp = Integer.compare(this.getPlaylistId(), (o.getPlaylistId()));
//        if (comp != 0) {
//            return comp;
//        }
    }
}

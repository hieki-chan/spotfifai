/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.models;

import java.util.Objects;

/**
 *
 * @author admin
 */
public class PlaylistDetail
{

    private int playlistId;
    private String songId;

    public PlaylistDetail(int playlistId, String songId)
    {
        this.playlistId = playlistId;
        this.songId = songId;
    }

    public int getPlaylistId()
    {
        return playlistId;
    }

    public String getSongId()
    {
        return songId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.songId) + Objects.hashCode(this.playlistId);
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
        final PlaylistDetail other = (PlaylistDetail) obj;
        if (this.playlistId != other.playlistId)
        {
            return false;
        }
        return Objects.equals(this.songId, other.songId);
    }
    
    
}

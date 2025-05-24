/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.models;

import java.util.List;

/**
 *
 * @author admin
 */
public class Playlist
{

    private int playlistId;
    private String title;
    private List<Integer> songIds;

    public Playlist(int playlistId, String name, List<Integer> songIds)
    {
        this.playlistId = playlistId;
        this.title = name;
        this.songIds = songIds;
    }

    public Playlist(String name)
    {
        this.title = name;
    }

    public Playlist()
    {

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

}

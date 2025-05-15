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
    private String name;
    private List<Integer> songIds;

    public Playlist(int playlistId, String name, List<Integer> songIds)
    {
        this.playlistId = playlistId;
        this.name = name;
        this.songIds = songIds;
    }

    public Playlist(String name, List<Integer> songIds)
    {
        this.name = name;
        this.songIds = songIds;
    }
    
    
}

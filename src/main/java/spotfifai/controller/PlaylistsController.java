/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import spotfifai.dao.PlaylistDAO;
import spotfifai.models.Playlist;
import spotfifai.util.located.IService;

/**
 *
 * @author admin
 */
public class PlaylistsController implements IService
{
    private PlaylistDAO playlistDAO;
    
    public PlaylistsController(PlaylistDAO playlistDAO)
    {
        this.playlistDAO = playlistDAO;
    }
    
    public void onCreateNew()
    {
        Playlist newPlaylist = new Playlist(getNewPlaylistName());
        
        playlistDAO.add(newPlaylist);
    }
    
    public void onDelete()
    {
        playlistDAO.delete();
    }
    
    private String getNewPlaylistName()
    {
        return "Playlist #" + (playlistDAO.getCount() + 1);
    }
}

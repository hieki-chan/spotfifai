/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import spotfifai.dao.PlaylistDAO;
import spotfifai.dao.PlaylistDetailDAO;
import spotfifai.models.Playlist;
import spotfifai.util.located.IService;

/**
 *
 * @author admin
 */
public class PlaylistsController implements IService
{
    private final PlaylistDAO playlistDAO;
    private final PlaylistDetailDAO playlistDetailDAO;
        
    public PlaylistsController(
            PlaylistDAO playlistDAO,
            PlaylistDetailDAO playlistDetailDAO)
    {
        this.playlistDAO = playlistDAO;
        this.playlistDetailDAO = playlistDetailDAO;
    }
    
    public PlaylistDAO getPlaylistDAO()
    {
        return playlistDAO;
    }
    
    public Playlist onCreateNew()
    {
        Playlist newPlaylist = new Playlist(getNewPlaylistName());
        
        if(playlistDAO.add(newPlaylist))
        {
            return newPlaylist;
        }
        
        return null;
    }
    
    public boolean onDelete(Playlist playlist)
    {
        if(playlist == null)
            return false;
        
        return playlistDAO.delete(playlist);
    }
    
    public void removeSongFromPlaylist()
    {
        
    }
    
    private String getNewPlaylistName()
    {
        return "Playlist #" + (playlistDAO.getCount() + 1);
    }
}

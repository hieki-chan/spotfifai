/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import spotfifai.dao.SongDAO;
import spotfifai.dao.UserDAO;
import spotfifai.models.Song;
import spotfifai.models.User;
import spotfifai.util.located.IService;

/**
 *
 * @author admin
 */
public class SongDistributorController implements IService
{
    private final SongDAO songDAO;
    private final UserDAO userDAO;
        
    public SongDistributorController(SongDAO songDAO, UserDAO userDAO)
    {
        this.songDAO = songDAO;
        this.userDAO = userDAO;
    }
    
    public SongDAO getSongDAO()
    {
        return songDAO;
    }
    
    public void upload(Song song)
    {
        songDAO.add(song);
    }
    
    
    public void deleteSong(Song song)
    {
        songDAO.delete(song);
    }
    
    public String getArtistNameFromSong(Song song)
    {
        User artist = userDAO.getEntity(song.getArtistId());
        return artist.getUsername();
    }
}

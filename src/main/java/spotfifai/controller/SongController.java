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
public class SongController implements IService
{
    private SongDAO songDAO;
    private UserDAO userDAO;
    
    public SongController(SongDAO songDAO, UserDAO userDAO)
    {
        this.songDAO = songDAO;
        this.userDAO = userDAO;
    }
    
    public String getArtistNameFromSong(Song song)
    {
        User artist = userDAO.get(song.getArtistId());
        return artist.getUsername();
    }
}

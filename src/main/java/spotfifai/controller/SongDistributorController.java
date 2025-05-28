/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import spotfifai.dao.SongDAO;
import spotfifai.dao.UserDAO;
import spotfifai.models.Song;
import spotfifai.models.User;
import spotfifai.util.located.IService;

/**
 *
 * @author admin
 */
public class SongDistributorController implements IService, IAuthListener
{

    private final SongDAO songDAO;
    private final UserDAO userDAO;

    private Map<Integer, Song> ownedSongsCache = new HashMap<>();

    public SongDistributorController(SongDAO songDAO, UserDAO userDAO)
    {
        this.songDAO = songDAO;
        this.userDAO = userDAO;
        
        SpotfifaiAuth.current().addListener(this);
    }

    public SongDAO getSongDAO()
    {
        return songDAO;
    }

    public Collection<Song> getOwnedSongs()
    {
        return ownedSongsCache.values();
    }

    public void upload(Song song)
    {
        song.setArtistId(SpotfifaiAuth.current().getCurrentUser().getUserId());
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

    @Override
    public void onSignedIn()
    {
        ownedSongsCache = songDAO.queryOwnedSongs(SpotfifaiAuth.current().getCurrentUser().getUserId());

    }

    @Override
    public void onSignedOut()
    {
        ownedSongsCache = null;
    }
}

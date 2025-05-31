/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import spotfifai.dao.PlaylistDetailDAO;
import spotfifai.dao.SongDAO;
import spotfifai.dao.UserDAO;
import spotfifai.models.PlaylistDetail;
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
    private final PlaylistDetailDAO playlistDetailDAO;

    public SongDistributorController(SongDAO songDAO, UserDAO userDAO, PlaylistDetailDAO playlistDetailDAO)
    {
        this.songDAO = songDAO;
        this.userDAO = userDAO;
        this.playlistDetailDAO = playlistDetailDAO;

        SpotfifaiAuth.current().addListener(this);
    }

    public SongDAO getSongDAO()
    {
        return songDAO;
    }

    public Map<String, Song> getOwnedSongs()
    {
        return songDAO.getOwnedSongsCache();
    }

    public Map<String, Song> getSongs(List<PlaylistDetail> detailList)
    {
        List<String> songIds = new ArrayList<>();
        for (PlaylistDetail pd : detailList)
        {
            songIds.add(pd.getSongId());
        }
        return songDAO.getSongs(songIds);
    }

    public boolean upload(Song song)
    {
        song.setArtistId(SpotfifaiAuth.current().getCurrentUser().getUserId());
        boolean isSuccess = songDAO.add(song);
        if (isSuccess)
        {
            
        }

        return isSuccess;
    }
    
    public boolean update(Song song)
    {
        return songDAO.update(song);
    }

    public boolean deleteSong(Song song)
    {
        playlistDetailDAO.deleteSongInPlaylists(song.getSongId());
        return songDAO.delete(song);
    }

    public String getArtistNameFromSong(Song song)
    {
        User artist = userDAO.getEntity(song.getArtistId());
        return artist.getUsername();
    }
    
    public boolean checkForSong(Song song)
    {
        return !song.getTitle().isEmpty() && !song.getTitle().isEmpty()
                && !song.getDescription().isBlank() && !song.getDescription().isEmpty()
                && song.getAudioData() != null
                && song.getIconData() != null;
    }

    @Override
    public void onSignedIn(User user)
    {
        songDAO.queryOwnedSongs(SpotfifaiAuth.current().getCurrentUser().getUserId());
    }

    @Override
    public void onSignedOut()
    {
        songDAO.clearCache();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.util.Collection;
import spotfifai.dao.PlaylistDAO;
import spotfifai.dao.PlaylistDetailDAO;
import spotfifai.states.ResultState;
import spotfifai.models.Playlist;
import spotfifai.models.PlaylistDetail;
import spotfifai.models.Song;
import spotfifai.models.User;
import spotfifai.util.located.IService;

/**
 *
 * @author admin
 */
public class PlaylistsController implements IService, IAuthListener
{

    private final PlaylistDAO playlistDAO;
    private final PlaylistDetailDAO playlistDetailDAO;
    
    public PlaylistsController(
            PlaylistDAO playlistDAO,
            PlaylistDetailDAO playlistDetailDAO)
    {
        this.playlistDAO = playlistDAO;
        this.playlistDetailDAO = playlistDetailDAO;
        
        SpotfifaiAuth.current().addListener(this);
    }

    
    public Collection<Playlist> getOwnedPlaylists()
    {
        return playlistDAO.getEntitiesAll();
    }

    public Playlist onCreateNew()
    {
        Playlist newPlaylist = new Playlist(getNewPlaylistName(), SpotfifaiAuth.current().getCurrentUser().getUserId());

        if (playlistDAO.add(newPlaylist))
        {
            return newPlaylist;
        }

        return null;
    }

    public boolean onPlaylistDelete(Playlist playlist)
    {
        if (playlist == null)
        {
            return false;
        }

        return playlistDetailDAO.deleteFromPlaylistAll(playlist.getPlaylistId()) >= 0 && playlistDAO.delete(playlist);
    }

    public ResultState addSongToPlaylist(Song song, Playlist playlist)
    {
        PlaylistDetail newPlaylistDetail = new PlaylistDetail(playlist.getPlaylistId(), song.getSongId());
        if (playlistDetailDAO.contains(newPlaylistDetail))
        {
            return ResultState.FAILED;
        }

        boolean isSuccess = playlistDetailDAO.add(newPlaylistDetail);
        if (!isSuccess)
        {
            return ResultState.ERROR;
        }

        playlist.getPlaylistDetails().add(newPlaylistDetail);
        return ResultState.SUCCESS;
    }

    public void removeSongFromPlaylist(PlaylistDetail playlistDetail, Playlist playlist)
    {
        if(playlistDetailDAO.delete(playlistDetail))
        {
            playlist.getPlaylistDetails().remove(playlistDetail);
        }
    }

    private String getNewPlaylistName()
    {
        return "Playlist #" + (playlistDAO.getCount() + 1);
    }

    @Override
    public void onSignedIn(User user)
    {
        playlistDAO.queryOwnedPlaylist(SpotfifaiAuth.current().getCurrentUser().getUserId());
    }

    @Override
    public void onSignedOut()
    {
        playlistDAO.clear();
    }
}

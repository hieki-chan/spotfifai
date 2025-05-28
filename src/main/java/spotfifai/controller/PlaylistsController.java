/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.util.Map;
import spotfifai.dao.PlaylistDAO;
import spotfifai.dao.PlaylistDetailDAO;
import spotfifai.states.ResultState;
import spotfifai.models.Playlist;
import spotfifai.models.PlaylistDetail;
import spotfifai.models.Song;
import spotfifai.util.located.IService;

/**
 *
 * @author admin
 */
public class PlaylistsController implements IService, IAuthListener
{

    private final PlaylistDAO playlistDAO;
    private final PlaylistDetailDAO playlistDetailDAO;
    
    private final Map<Integer, T> cachedEntities;

    public PlaylistsController(
            PlaylistDAO playlistDAO,
            PlaylistDetailDAO playlistDetailDAO)
    {
        this.playlistDAO = playlistDAO;
        this.playlistDetailDAO = playlistDetailDAO;

        for (var playlistDetail : playlistDetailDAO.getEntitiesAll())
        {
            //System.out.println(playlistDetail.getPlaylistId());
            //playlistDAO.debug();
            Playlist playlist = playlistDAO.getEntity(playlistDetail.getPlaylistId());
            playlist.getPlaylistDetails().add(playlistDetail);
        };
        
        SpotfifaiAuth.current().addListener(this);
    }

    public PlaylistDAO getPlaylistDAO()
    {
        return playlistDAO;
    }
    
    public void getOwnedPlaylists()
    {
        
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

        return playlistDetailDAO.delete(playlist.getPlaylistId()) >= 0 && playlistDAO.delete(playlist);
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

    public void removeSongFromPlaylist()
    {

    }

    private String getNewPlaylistName()
    {
        return "Playlist #" + (playlistDAO.getCount() + 1);
    }

    @Override
    public void onSignedIn()
    {
        playlistDAO.queryOwnedPlaylist(SpotfifaiAuth.current().getCurrentUser().getUserId());
    }

    @Override
    public void onSignedOut()
    {
        playlistDAO.
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

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

        for (var playlistDetail : playlistDetailDAO.getEntitiesAll())
        {
            //System.out.println(playlistDetail.getPlaylistId());
            //playlistDAO.debug();
            Playlist playlist = playlistDAO.getEntity(playlistDetail.getPlaylistId());
            playlist.getPlaylistDetails().add(playlistDetail);
        };
    }

    public PlaylistDAO getPlaylistDAO()
    {
        return playlistDAO;
    }

    public Playlist onCreateNew()
    {
        Playlist newPlaylist = new Playlist(getNewPlaylistName());

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

        return playlistDetailDAO.delete(playlist.getPlaylistId()) && playlistDAO.delete(playlist);
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
}

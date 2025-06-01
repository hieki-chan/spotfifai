/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.models.Playlist;
import spotfifai.models.PlaylistDetail;

/**
 *
 * @author admin
 */
public final class PlaylistDAO extends BaseDAO<Integer, Playlist>
{

    public PlaylistDAO()
    {
        super();
    }

    public Map<Integer, Playlist> queryOwnedPlaylist(String userId)
    {
        final String sql = "SELECT * FROM Playlist p "
                + "LEFT JOIN PlaylistDetail pd ON p.playlistId = pd.playlistId "
                + "WHERE p.userId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql);)
        {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            Map<Integer, Playlist> playlists = new HashMap<>();

            while (rs.next())
            {
                Integer playlistId = rs.getInt("playlistId");
                Playlist playlist = playlists.get(playlistId);
                if (playlist == null)
                {
                    playlist = new Playlist(
                            playlistId,
                            rs.getString("title"),
                            rs.getString("userId")
                    );

                    playlists.put(playlistId, playlist);
                }
                String songId = rs.getString("songId");
                if (songId != null)
                {
                    playlist.getPlaylistDetails().add(new PlaylistDetail(
                            playlistId,
                            songId
                    ));
                }

                cachedEntities.put(playlistId, playlist);
            }

            return playlists;

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public boolean delete(Playlist entity)
    {
        final String sql = "DELETE FROM Playlist WHERE playlistId = ?";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setInt(1, entity.getPlaylistId());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0)
            {
                cachedEntities.remove(entity.getPlaylistId());
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }

    public boolean add(Playlist entity)
    {
        if (cachedEntities.containsKey(entity.getPlaylistId()))
        {
            return false;
        }

        final String sql = "INSERT INTO Playlist (title, userId) VALUES (?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getUserId());
            int affected = stmt.executeUpdate();
            if (affected <= 0)
            {
                return false;
            }

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
            {
                int id = rs.getInt(1);
                entity.setPlaylistId(id);
                cachedEntities.put(entity.getPlaylistId(), entity);

                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return false;
    }

    public void clear()
    {
        cachedEntities.clear();
    }
}

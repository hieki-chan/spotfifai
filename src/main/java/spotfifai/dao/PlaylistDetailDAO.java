/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.models.PlaylistDetail;

/**
 *
 * @author admin
 */
public class PlaylistDetailDAO extends BaseDAO<PlaylistDetail>
{

    @Override
    public boolean update(PlaylistDetail entity)
    {
        return false;
    }

    @Override
    public boolean delete(PlaylistDetail entity)
    {
        System.out.println("del" + entity.getPlaylistId() + " " + entity.getSongId());
        String sql = "DELETE FROM PlaylistDetail WHERE playlistId = ? and songId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setInt(1, entity.getPlaylistId());
            stmt.setString(2, entity.getSongId());
            
            int affected = stmt.executeUpdate();
            if (affected > 0)
            {
                removeFromCacheInternal(entity);
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public int deleteFromPlaylistAll(int playlistId)
    {
        String sql = "DELETE FROM PlaylistDetail WHERE playlistId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setInt(1, playlistId);

            int affected = stmt.executeUpdate();
            if (affected > 0)
            {
                List<PlaylistDetail> entitiesToRemoved = new ArrayList<>();
                for (var entity : getEntitiesAll())
                {
                    if (entity.getPlaylistId() == playlistId)
                    {
                        entitiesToRemoved.add(entity);
                    }
                }

                for (var entity : entitiesToRemoved)
                {
                    if (entity.getPlaylistId() == playlistId)
                    {
                        removeFromCacheInternal(entity);
                    }
                }
            }
            return affected;

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    public boolean deleteSongInPlaylists(String songId)
    {
        final String sql = "DELETE FROM PlaylistDetail WHERE songId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql)) {

            stmt.setString(1, songId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected >= 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public boolean add(PlaylistDetail entity)
    {
        if (contains(entity))
        {
            return false;
        }

        final String sql = "INSERT INTO PlaylistDetail (playlistId, SongId) VALUES (?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setInt(1, entity.getPlaylistId());
            stmt.setString(2, entity.getSongId());

            int affected = stmt.executeUpdate();
            if (affected > 0)
            {
                addToCacheInternal(entity);
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}

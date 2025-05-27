/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.dbengine.JDBQuery;
import spotfifai.models.PlaylistDetail;

/**
 *
 * @author admin
 */
public class PlaylistDetailDAO extends BaseDAO<PlaylistDetail>
{

    @Override
    void onQuerySelector()
    {
        final String sql = "SELECT * FROM PlaylistDetail";

        JDBQuery.selectAllFrom(super.getConnection(), sql, (rs) ->
        {
            PlaylistDetail playlistDetail = new PlaylistDetail(
                    rs.getInt(1),
                    rs.getString(2)
            );
            addToCacheInternal(playlistDetail);
        });
    }

    @Override
    public boolean update(PlaylistDetail entity)
    {
        return false;
    }

    public boolean deleteRange(List<PlaylistDetail> entities)
    {
        String sql = "DELETE FROM PlaylistDetail WHERE playlistId IN ("
                + String.join(",", Collections.nCopies(entities.size(), "?")) + ")";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            for (int i = 0; i < entities.size(); i++)
            {
                stmt.setInt(1, entities.get(i).getPlaylistId());
            }

            int affected = stmt.executeUpdate();
            if (affected > 0)
            {
                for (int i = 0; i < entities.size(); i++)
                {
                    removeFromCacheInternal(entities.get(i));
                }
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    @Override
    boolean delete(PlaylistDetail entity)
    {
        //not implemented;
        return false;
    }

    public boolean delete(int playlistId)
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
                    if(entity.getPlaylistId() == playlistId)
                        entitiesToRemoved.add(entity);
                }
                
                for (var entity : entitiesToRemoved)
                {
                    if(entity.getPlaylistId() == playlistId)
                        removeFromCacheInternal(entity);
                }
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
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

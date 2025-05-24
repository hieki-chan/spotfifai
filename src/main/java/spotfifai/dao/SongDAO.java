/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.dbengine.JDBQuery;
import spotfifai.models.Song;

/**
 *
 * @author admin
 */
public final class SongDAO extends BaseDAO<Song>
{

    public SongDAO()
    {
        super();
    }

    @Override
    void onQuerySelector()
    {
        final String sql = "SELECT * FROM Song";

        JDBQuery.selectAllFrom(super.getConnection(), sql, (rs) ->
        {
            Song song = new Song(
                    rs.getString("songId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBytes("audioData")
            );
            addToCacheInternal(song);
        });
    }

    @Override
    public boolean update(Song entity)
    {
        final String sql = "UPDATE Song SET title = ?, description = ?, audioData = ? WHERE songId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getDescription());
            stmt.setBytes(3, entity.getAudioData());
            stmt.setString(4, entity.getSongId());

            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                // updated
                addToCacheInternal(entity);
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean delete(Song entity)
    {
        final String sql = "DELETE FROM Song WHERE songId = ?";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getSongId());
            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                removeFromCacheInternal(entity);
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean add(Song entity)
    {
        if (contains(entity))
        {
            return false;
        }

        final String sql = "INSERT INTO Song (songId, title, description, audioData) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getSongId());
            stmt.setString(2, entity.getTitle());
            stmt.setString(3, entity.getDescription());
            stmt.setBytes(4, entity.getAudioData());
            stmt.executeUpdate();

            addToCacheInternal(entity);
            return true;

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

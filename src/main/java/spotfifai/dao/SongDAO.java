/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public Map<String, Song> queryAllSongs()
    {
        Map<String, Song> result = new HashMap<>();
        final String sql = "SELECT songId, title, description, userId FROM Song";

        JDBQuery.selectAllFrom(super.getConnection(), sql, (rs) ->
        {
            Song song = new Song(
                    rs.getString("songId"),
                    rs.getString("title"),
                    rs.getString("description"),
                    null,
                    rs.getString("userId")
            );
            result.put(song.getSongId(), song);
        });

        return result;
    }

    public Map<String, Song> queryOwnedSongs(String userId)
    {
        final String sql = "SELECT * FROM Song WHERE userId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            Map<String, Song> ownedSongs = new HashMap<>();
            while (rs.next())
            {
                Song song = new Song(
                        rs.getString("songId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBytes("audioData"),
                        userId
                );
                ownedSongs.put(song.getSongId(), song);
            }
            return ownedSongs;

        } catch (SQLException ex)
        {

        }
        return null;
    }

    public Map<String, Song> getSongs(List<String> songIds)
    {
        if(songIds.size() == 0)
            return null;
        
        String sql = "SELECT songId, title, description, userId FROM Song WHERE songId IN (" + String.join(",", Collections.nCopies(songIds.size(), "?")) + ")";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            for (int i = 0; i < songIds.size(); i++)
            {
                stmt.setString(i + 1, songIds.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            Map<String, Song> result = new HashMap<>();

            while (rs.next())
            {
                Song song = new Song(
                        rs.getString("songId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        null,
                        rs.getString("userId")
                );
                result.put(song.getSongId(), song);
            }

            return result;
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public byte[] getAudioData(String songId)
    {
        final String sql = "SELECT audioData FROM Song WHERE songId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, songId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                return rs.getBytes("audioData");
            }

        } catch (SQLException ex)
        {

        }
        return null;
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
                //addToCacheInternal(entity);
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

        final String sql = "INSERT INTO Song (songId, title, description, audioData, userId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getSongId());
            stmt.setString(2, entity.getTitle());
            stmt.setString(3, entity.getDescription());
            stmt.setBytes(4, entity.getAudioData());
            stmt.setString(5, entity.getArtistId());
            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                addToCacheInternal(entity);
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
}

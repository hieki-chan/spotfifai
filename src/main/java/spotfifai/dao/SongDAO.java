/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.models.Song;

/**
 *
 * @author admin
 */
public final class SongDAO extends BaseDAO<String, Song>
{

    public SongDAO()
    {
        super();
    }

    public Map<String, Song> queryAllSongs()
    {
        Map<String, Song> result = new HashMap<>();
        final String sql = "SELECT songId, title, description, userId, iconData FROM Song";
        try
        {
            Statement statement = super.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                Song song = new Song(
                        rs.getString("songId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        null,
                        rs.getString("userId"),
                        rs.getBytes("iconData")
                );
                result.put(song.getSongId(), song);
            }
        } catch (SQLException ex)
        {
            return null;
        }

        return result;
    }

    public void queryOwnedSongs(String userId)
    {
        final String sql = "SELECT * FROM Song WHERE userId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                Song song = new Song(
                        rs.getString("songId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBytes("audioData"),
                        userId,
                        rs.getBytes("iconData")
                );
                cachedEntities.put(song.getSongId(), song);
            }

        } catch (SQLException ex)
        {

        }
    }

    public Map<String, Song> getOwnedSongsCache()
    {
        return cachedEntities;
    }

    @Override
    public boolean contains(Song entity)
    {
        return cachedEntities.containsKey(entity.getSongId());
    }

    public void clearCache()
    {
        cachedEntities.clear();
    }

    public Map<String, Song> getSongs(List<String> songIds)
    {
        if (songIds.isEmpty())
        {
            return null;
        }

        String sql = "SELECT songId, title, description, userId, iconData FROM Song WHERE songId IN (" + String.join(",", Collections.nCopies(songIds.size(), "?")) + ")";
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
                        rs.getString("userId"),
                        rs.getBytes("iconData")
                );
                result.put(song.getSongId(), song);
            }

            return result;
        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Song getSongWithAudioData(String songId)
    {
        final String sql = "SELECT * FROM Song WHERE songId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, songId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                Song song = new Song(
                        rs.getString("songId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBytes("audioData"),
                        rs.getString("userId"),
                        rs.getBytes("iconData")
                );
                return song;
            }

        } catch (SQLException ex)
        {

        }
        return null;
    }

    public boolean update(Song entity)
    {
        final String sql = "UPDATE Song SET title = ?, description = ?, audioData = ?, iconData = ? WHERE songId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getTitle());
            stmt.setString(2, entity.getDescription());
            stmt.setBytes(3, entity.getAudioData());
            stmt.setBytes(4, entity.getIconData());
            stmt.setString(5, entity.getSongId());

            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                // updated
                //addToCacheInternal(entity);
                if (cachedEntities.containsKey(entity.getSongId()))
                {
                    cachedEntities.put(entity.getSongId(), entity);
                }
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(Song entity)
    {
        final String sql = "DELETE FROM Song WHERE songId = ?";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getSongId());
            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                cachedEntities.remove(entity.getSongId());
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean add(Song entity)
    {
        if (contains(entity))
        {
            return false;
        }

        final String sql = "INSERT INTO Song (songId, title, description, audioData, userId, iconData) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getSongId());
            stmt.setString(2, entity.getTitle());
            stmt.setString(3, entity.getDescription());
            stmt.setBytes(4, entity.getAudioData());
            stmt.setString(5, entity.getArtistId());
            stmt.setBytes(6, entity.getIconData());
            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                cachedEntities.put(entity.getSongId(), entity);
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean checkUniqueTitle(Song song)
    {
        String sql = "SELECT 1 FROM Song WHERE songId != ? AND title = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, song.getSongId());
            stmt.setString(2, song.getTitle());
            ResultSet rs = stmt.executeQuery();

            return !rs.next();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

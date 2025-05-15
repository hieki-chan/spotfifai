/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.dbengine.DBConnector;
import spotfifai.dbengine.JDBQuery;
import spotfifai.models.Song;
import spotfifai.util.located.ServiceLocator;

/**
 *
 * @author admin
 */
public class SongDAO extends BaseDAO<Song>
{

    public SongDAO()
    {
        super();
    }

    @Override
    Map<Integer, Song> querySelector()
    {
        Map<Integer, Song> result = new HashMap<>();

        Connection connection = super.getConnection();

        final String sql = "SELECT * FROM Song";
        JDBQuery.selectAllFrom(connection, sql, (rs) ->
        {
            Song song = new Song(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)
            );
            result.put(song.hashCode(), song);
        });

        return result;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void delete()
    {

    }

    @Override
    public void add(Song entity)
    {
        Connection connection = super.getConnection();

        final String sql = "INSERT INTO Song (name, description) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.executeUpdate();

            cachedEntities.put(entity.hashCode(), entity);

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

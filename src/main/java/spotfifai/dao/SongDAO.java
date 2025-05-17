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
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)
            );
            addToCacheInternal(song);
        });
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
        if(contains(entity))
            return;
                
        final String sql = "INSERT INTO Song (name, description) VALUES (?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getDescription());
            stmt.executeUpdate();

            addToCacheInternal(entity);

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

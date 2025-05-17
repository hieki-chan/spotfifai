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
import spotfifai.models.Playlist;

/**
 *
 * @author admin
 */
public final class PlaylistDAO extends BaseDAO<Playlist>
{
    public PlaylistDAO()
    {
        super();
    }

    @Override
    void onQuerySelector()
    {
        final String sql = "SELECT * FROM Playlist";

        JDBQuery.selectAllFrom(super.getConnection(), sql, (rs) ->
        {
            Playlist playlist = new Playlist(
                    rs.getInt(1),
                    rs.getString(2),
                    null
            );
           addToCacheInternal(playlist);
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
    public void add(Playlist entity)
    {
        if(contains(entity))
            return;
        
        final String sql = "INSERT INTO Playlist () VALUES (?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getName());
            stmt.executeUpdate();

            addToCacheInternal(entity);

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

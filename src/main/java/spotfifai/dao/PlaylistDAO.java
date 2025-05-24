/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public boolean update(Playlist entity)
    {
        return false;
    }

    @Override
    public boolean delete(Playlist entity)
    {
        return false;
    }

    @Override
    public boolean add(Playlist entity)
    {
        if (contains(entity))
        {
            return false;
        }

        final String sql = "INSERT INTO Playlist (title) VALUES (?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, entity.getTitle());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
            {
                int id = rs.getInt(1);
                entity.setPlaylistId(id);
            }

            addToCacheInternal(entity);
            return true;

        } catch (SQLException ex)
        {
            Logger.getLogger(PlaylistDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}

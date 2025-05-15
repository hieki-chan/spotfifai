/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import spotfifai.dbengine.JDBQuery;
import spotfifai.models.Playlist;

/**
 *
 * @author admin
 */
public class PlaylistDAO extends BaseDAO<Playlist>
{

    public PlaylistDAO()
    {
        super();
    }

    @Override
    Map<Integer, Playlist> querySelector()
    {
        Map<Integer, Playlist> result = new HashMap<>();

        Connection connection = super.getConnection();

        final String sql = "SELECT * FROM Playlist";

        JDBQuery.selectAllFrom(connection, sql, (rs) ->
        {
            Playlist song = new Playlist(
                    rs.getInt(1),
                    rs.getString(2),
                    null
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
    public void add(Playlist entity)
    {

    }

}

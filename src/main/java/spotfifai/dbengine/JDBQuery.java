/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dbengine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class JDBQuery
{
    public static void selectAllFrom(Connection connection, String sql, IQueryResult onResultRead)
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                // callback
                onResultRead.onNext(resultSet);
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(JDBQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

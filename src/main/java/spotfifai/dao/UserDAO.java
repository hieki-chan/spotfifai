/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import spotfifai.dbengine.JDBQuery;
import spotfifai.models.User;

/**
 *
 * @author admin
 */
public class UserDAO extends BaseDAO<User>
{

    void onQuerySelector()
    {
        final String sql = "SELECT * FROM [User]";

        JDBQuery.selectAllFrom(super.getConnection(), sql, (rs) ->
        {
            User user = new User(
                    rs.getString("userId"),
                    rs.getString("username"),
                    rs.getString("password")
            );
            addToCacheInternal(user);
        });
    }

    @Override
    public boolean update(User entity)
    {
        return false;
    }

    @Override
    public boolean delete(User entity)
    {
        return false;
    }

    @Override
    public boolean add(User entity)
    {
        String sql = "INSERT INTO [User] VALUES (?, ?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getUserId());
            stmt.setString(2, entity.getUsername());
            stmt.setString(3, entity.getPassword());
            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                addToCacheInternal(entity);
                return true;
            }
        } catch (Exception e)
        {
        }

        return false;
    }

    public User checkForUser(String username, String password)
    {
        String sql = "SELECT * FROM [User] WHERE username = ? and password = ?";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                return new User(
                        rs.getString("userId"), 
                        rs.getString("username"), 
                        rs.getString("password"));
            }
        } catch (Exception e)
        {
            return null;
        }

        return null;
    }
}

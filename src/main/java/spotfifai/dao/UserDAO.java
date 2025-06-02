/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.models.User;

/**
 *
 * @author admin
 */
public class UserDAO extends BaseDAO<String, User>
{

    public Map<String, User> queryAllUser()
    {
        final String sql = "SELECT * FROM [User]";
        Map<String, User> users = new HashMap<>();

        try
        {
            Statement statement = super.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
            {
                User user = new User(
                        rs.getString("userId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBytes("iconData"),
                        rs.getInt("role")
                );
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    public User queryUser(String userId)
    {
        String sql = "SELECT * FROM [User] WHERE userId = ?";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                return new User(
                        rs.getString("userId"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBytes("iconData"),
                        rs.getInt("role")
                );
            }
        } catch (Exception e)
        {
            return null;
        }

        return null;
    }

    public boolean update(User entity)
    {
        final String sql = "UPDATE [User] SET username = ?, password = ?, iconData = ? WHERE userId = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setBytes(3, entity.getIconData());
            stmt.setString(4, entity.getUserId());

            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                // updated
                return true;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(SongDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(User entity)
    {
        return false;
    }

    public boolean add(User entity)
    {
        String sql = "INSERT INTO [User] VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, entity.getUserId());
            stmt.setString(2, entity.getUsername());
            stmt.setString(3, entity.getPassword());
            stmt.setBytes(4, entity.getIconData());
            stmt.setInt(5, entity.getRole().getCode());
            int affected = stmt.executeUpdate();

            if (affected > 0)
            {
                return true;
            }
        } catch (Exception e)
        {
            System.out.println("add user falied" + e.getMessage());
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
                        rs.getString("password"),
                        rs.getBytes("iconData"),
                        rs.getInt("role")
                );
            }
        } catch (Exception e)
        {
            return null;
        }

        return null;
    }
    
    public boolean checkUniqueUser(String userId, String username)
    {
        String sql = "SELECT 1 FROM [User] WHERE userId != ? AND username = ?";

        try (PreparedStatement stmt = super.getConnection().prepareStatement(sql))
        {
            stmt.setString(1, userId);
            stmt.setString(2, username);
            ResultSet rs = stmt.executeQuery();

            return !rs.next();

        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

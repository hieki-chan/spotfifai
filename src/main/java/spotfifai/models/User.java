/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.models;

/**
 *
 * @author admin
 */
public class User
{
    private String userId;
    private String username;
    private String password;

    public User(String userId, String username, String password)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getPassword()
    {
        return password;
    }
    
    
    public String getUsername()
    {
        return username;
    }
}

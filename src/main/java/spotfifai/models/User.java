/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.models;

import java.util.Objects;

/**
 *
 * @author admin
 */
public class User
{
    private String userId;
    private String username;
    private String password;
    private byte[] iconData;
    private Role role;

    public User(String userId, String username, String password, byte[] iconData, int role)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.iconData = iconData;
        this.role = Role.fromCode(role);
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

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public byte[] getIconData()
    {
        return iconData;
    }

    public void setIconData(byte[] iconData)
    {
        this.iconData = iconData;
    }
    
    

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }
    

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.userId);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.userId, other.userId);
    }
    
}

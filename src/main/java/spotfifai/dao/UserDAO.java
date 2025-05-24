/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.dao;

import spotfifai.models.User;
/**
 *
 * @author admin
 */
public class UserDAO extends BaseDAO<User>
{

    @Override
    void onQuerySelector()
    {
        
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
        return false;
    }
    
}

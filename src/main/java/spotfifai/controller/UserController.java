/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import spotfifai.dao.UserDAO;
import spotfifai.models.User;
import spotfifai.util.located.IService;

/**
 *
 * @author admin
 */
public class UserController implements IService
{
    private final UserDAO userDAO;
    
    public UserController(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }
    
    public void updateUser(User user)
    {
        System.out.println("update");
        userDAO.update(user);
    }
    
    public User getUser(String userId)
    {
        return userDAO.queryUser(userId);
    }
}

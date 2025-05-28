/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import spotfifai.dao.UserDAO;
import spotfifai.models.User;
import spotfifai.states.ResultState;

/**
 *
 * @author admin
 */
public class SpotfifaiAuth
{

    private static SpotfifaiAuth instance;
    
    public static SpotfifaiAuth current()
    {
        return instance;
    }

    List<IAuthListener> authListeners = new ArrayList<>();
    private final UserDAO userDAO;
    private User currentUser;

    public User getCurrentUser()
    {
        return currentUser;
    }
    
    public boolean isSignedIn()
    {
        return getCurrentUser() != null;
    }

    public SpotfifaiAuth(UserDAO userDAO)
    {
        this.userDAO = userDAO;
        instance = this;
    }

    public ResultState signUp(String username, String password)
    {
        if (!isUsernameValid(username) || !isPasswordValid(password))
        {
            return ResultState.FAILED;
        }

        String randomId = UUID.randomUUID().toString().substring(0, 10);
        User user = new User(randomId, username, password);
        boolean isSuccess = userDAO.add(user);

        if (isSuccess)
        {
            currentUser = user;
            return ResultState.SUCCESS;
        }

        return ResultState.FAILED;
    }

    public boolean signIn(String username, String password)
    {
        User u = userDAO.checkForUser(username, password);

        // sign in successfully
        if (u != null)
        {
            currentUser = u;

            for (var l : authListeners)
            {
                l.onSignedIn();
            }
        }

        return u != null;
    }

    public void signOut()
    {
        currentUser = null;
        for (var l : authListeners)
        {
            l.onSignedOut();
        }
    }

    public boolean isUsernameValid(String username)
    {
        //length >=8
        if (username.length() < 8)
        {
            return false;
        }

        //username is unique
        for (User user : userDAO.getEntitiesAll())
        {
            if (user.getUsername().compareTo(username) == 0)
            {
                return false;
            }
        }

        return true;
    }

    public boolean isPasswordValid(String password)
    {
        //length >= 8
        if (password.length() < 8)
        {
            return false;
        }

        // atleast 1 number
        boolean any = false;
        for (char c : password.toCharArray())
        {
            if (Character.isDigit(c))
            {
                any = true;
                break;
            }
        }

        if (any == false)
        {
            return false;
        }

        return true;
    }

    public void addListener(IAuthListener l)
    {
        authListeners.add(l);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.dao.UserDAO;
import spotfifai.models.Role;
import spotfifai.models.User;
import spotfifai.states.ResultState;
import spotfifai.util.located.ResourceLocator;

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
        byte[] iconData = null;
        try
        {
            iconData = Files.readAllBytes(ResourceLocator.getDefaultUserIconPath());
        } catch (IOException ex)
        {
            Logger.getLogger(SpotfifaiAuth.class.getName()).log(Level.SEVERE, null, ex);
            return ResultState.FAILED;
        }
        User user = new User(randomId, username, password, iconData, 0);
        boolean isSuccess = userDAO.add(user);//database check

        if (isSuccess)
        {
            System.out.println("register success");
            //currentUser = user;
            signIn(user.getUsername(), user.getPassword());
            return ResultState.SUCCESS;
        }

        return ResultState.FAILED;
    }

    public boolean signIn(String username, String password)
    {
        User u = userDAO.checkForUser(username, password);
        if(u == null)
                return false;
        if (u.getIconData() == null)
        {
            try
            {
                u.setIconData(Files.readAllBytes(ResourceLocator.getDefaultUserIconPath()));
            } catch (IOException ex)
            {
                return false;
            }
        }

        // sign in successfully
        if (u != null)
        {
            currentUser = u;
            System.out.println("signed in");

            for (var l : authListeners)
            {
                l.onSignedIn(currentUser);
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
    
    public boolean isSignedAsAdmin()
    {
        return isSignedIn() && currentUser.getRole() == Role.ADMIN;
    }

    public boolean isUsernameValid(String username)
    {
        //length >=8
        if (!checkLength(username))
        {
            return false;
        }

        //username is unique
        for (User user : userDAO.getEntitiesAll().values())
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
        if (!checkLength(password))
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

    public boolean checkLength(String text)
    {
        //length >=8
        return text.length() >= 8;
    }

    public void addListener(IAuthListener l)
    {
        authListeners.add(l);
    }
}

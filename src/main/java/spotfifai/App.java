/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package spotfifai;

import spotfifai.controller.*;
import spotfifai.dao.*;
import spotfifai.dbengine.DBConnector;
import spotfifai.controller.MusicPlayerController;
import spotfifai.ui.MainFrame;
import spotfifai.util.located.ServiceLocator;
import spotfifai.util.theme.*;

/**
 *
 * @author admin
 */
public class App
{

    public final static String APP_NAME = "Spotfifai";

    public static void main(String[] args)
    {
        Theme.SetTheme(FlatTheme.DARK);

        // register controllers
        registerControllers();

        // main ui
        var mainFrame = new MainFrame(ServiceLocator.get(PlaylistsController.class));
        mainFrame.setTitle(APP_NAME);
    }
    
    static void registerControllers()
    {
        ServiceLocator.register(new DBConnector());
        
        SongDAO songDAO = new SongDAO();
        UserDAO userDAO = new UserDAO();
        PlaylistDAO playlistDAO = new PlaylistDAO();
        
        ServiceLocator.register(new SongController(songDAO, userDAO));
        ServiceLocator.register(new PlaylistsController(playlistDAO));
        ServiceLocator.register(new MusicPlayerController());
    }
}

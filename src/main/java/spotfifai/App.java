/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package spotfifai;

import spotfifai.controller.*;
import spotfifai.dao.*;
import spotfifai.dbengine.DBConnector;
import spotfifai.controller.MusicPlayerController;
import spotfifai.ui.MainFrame;
import spotfifai.util.audioplayer.AudioPlayer;
import spotfifai.util.located.ServiceLocator;
import spotfifai.util.theme.*;

/**
 *
 * @author hiekichan
 */
public class App
{
    public final static String APP_NAME = "Spotfifai";

    public static void main(String[] args)
    {
        // register dbengine, controllers, etc
        installServices();

        // dark theme
        Theme.SetTheme(FlatTheme.DARK);
        
        //main ui
        var mainFrame = new MainFrame();
        mainFrame.setTitle(APP_NAME);
    }
    
    static void installServices()
    {
        ServiceLocator.register(new DBConnector());
        
        var songDAO = new SongDAO();
        var userDAO = new UserDAO();
        var playlistDAO = new PlaylistDAO();
        var playlisyDetailDAO = new PlaylistDetailDAO();
        
        ServiceLocator.register(new SongController(songDAO, userDAO));
        ServiceLocator.register(new PlaylistsController(playlistDAO, playlisyDetailDAO));
        
        AudioPlayer audioPlayer = new AudioPlayer();
        ServiceLocator.register(new MusicPlayerController(audioPlayer));
    }
}

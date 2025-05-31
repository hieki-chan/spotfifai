/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package spotfifai;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import spotfifai.theme.FlatTheme;
import spotfifai.theme.Theme;
import spotfifai.controller.*;
import spotfifai.dao.*;
import spotfifai.dbengine.DBConnector;
import spotfifai.controller.MusicPlayerController;
import spotfifai.ui.MainFrame;
import spotfifai.util.audioplayer.AudioPlayer;
import spotfifai.util.located.ServiceLocator;

/**
 *
 * @author hiekichan
 */
public class App
{

    public final static String APP_NAME = "Spotfifai";

    public static void main(String[] args)
    {
        // register dbengine, controllers, etc...
        installServices();

        // dark theme
        Theme.SetTheme(FlatTheme.DARK);

        //main ui
        var mainFrame = new MainFrame();
        mainFrame.setTitle(APP_NAME);
        mainFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                ServiceLocator.get(MusicPlayerController.class).dispose();
            }
        });

        SpotfifaiAuth.current().signIn("admin", "123");

    }

    static void installServices()
    {
        // JDBC
        ServiceLocator.register(new DBConnector());

        // DAOs
        var songDAO = new SongDAO();
        var userDAO = new UserDAO();
        var playlistDAO = new PlaylistDAO();
        var playlisyDetailDAO = new PlaylistDetailDAO();

        // Music player
        AudioPlayer audioPlayer = new AudioPlayer();
        ServiceLocator.register(new MusicPlayerController(audioPlayer, songDAO));

        //singleton
        SpotfifaiAuth spotfifaiAuthenticator = new SpotfifaiAuth(userDAO);

        ServiceLocator.register(new UserController(userDAO));
        ServiceLocator.register(new SongDistributorController(songDAO, userDAO, playlisyDetailDAO));
        ServiceLocator.register(new PlaylistsController(playlistDAO, playlisyDetailDAO));
        ServiceLocator.register(new HomeController(songDAO));
    }
}

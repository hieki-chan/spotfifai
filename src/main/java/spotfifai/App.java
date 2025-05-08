/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package spotfifai.runtime;

import spotfifai.util.theme.Theme;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Image;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import spotfifai.util.theme.FlatTheme;
import spotfifai.ui.MainFrame;

import javax.swing.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import spotfifai.ui.MainFrame;
import spotfifai.util.audioplayer.AudioPlayer;
import spotfifai.util.locator.ResourceLocator;
import spotfifai.util.audioplayer.AudioPlayerDemo;
import spotfifai.util.audioplayer.PlayerException;

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
        

        var mainFrame = new MainFrame();
        mainFrame.setTitle(APP_NAME);
        
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Frame Center

    }
}

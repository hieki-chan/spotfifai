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

        //var mainFrame = new MainFrame();
        //mainFrame.setTitle(APP_NAME);
        
        
        try
        {
            File musicFile = new File("S:\\Java\\spotfifai\\src\\main\\java\\resources\\Icy Air.mp3");
            if (musicFile.exists())
            {
                AudioInputStream source = AudioSystem.getAudioInputStream(musicFile);
                Clip clip = AudioSystem.getClip();
                clip.open();
                clip.start();

            } else
            {
                System.err.println("Cannot open file");
            }
        } catch (Exception e)
        {
            System.err.println(e);
        }

        JOptionPane.showMessageDialog(null, "ok");
    }
}

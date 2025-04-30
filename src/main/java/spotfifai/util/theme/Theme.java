/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.util.theme;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author chatgpt
 */
public final class Theme 
{
    public static void SetTheme(FlatTheme flatTheme) 
    {
        try 
        {
            LookAndFeel lookAndFeel;
            lookAndFeel = switch (flatTheme)
            {
                case DARK -> new FlatDarkLaf();
                case LIGHT -> new FlatLightLaf();
                case DRACULA -> new FlatDarculaLaf();
                case INTELLIJ -> new FlatIntelliJLaf();
                default -> new FlatDarculaLaf();
            };

            // Set up FlatLaf look-and-feel (light theme)
            UIManager.setLookAndFeel(lookAndFeel);
            // Alternatively, you can use: UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) 
        {
            System.err.println("Failed to initialize FlatLaf");
        }
    }
}

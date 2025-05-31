/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.util;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author admin
 */
public class ImageUtil
{

    public static ImageIcon getIcon(byte[] imageBytes, int width, int height)
    {
       return scaleImageIcon(new ImageIcon(imageBytes), width, height);
    }
    

    public static ImageIcon scaleImageIcon(ImageIcon icon, int width, int height)
    {
        if (icon == null || icon.getImage() == null)
        {
            return null;
        }

        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}

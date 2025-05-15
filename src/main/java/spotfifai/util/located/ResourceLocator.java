/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.util.located;

import java.io.File;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author admin
 */
public class ResourceLocator
{
    private static final String resourcesPath = "../spotfifai/src/main/resources/resources/";
    
    public static String getResourceRelativePath(String fileName)
    {
        File file = new File(resourcesPath + fileName);        
        return file.getAbsolutePath();
    }
    
    public static String getIconRelativePath(String fileName)
    {
        File file = new File(resourcesPath + "icons/" + fileName);        
        return file.getAbsolutePath();
    }
    
    public static Icon getIcon(String fileName)
    {
        return new ImageIcon(ResourceLocator.getIconRelativePath(fileName));
    }
}

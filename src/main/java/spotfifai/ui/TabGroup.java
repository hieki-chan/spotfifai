/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.ui;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class TabGroup
{
    ArrayList<JPanel> tabViews;
    
    public TabGroup()
    {
        tabViews = new ArrayList<>();
    }
    
    public void Register(JPanel tab)
    {
        tabViews.add(tab);
    }
}

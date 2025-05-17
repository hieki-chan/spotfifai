/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public final class TabViewSystem
{

    private JPanel contentContainer;
    private final Map<Class<?>, JPanel> tabbedContents;

    public TabViewSystem(JPanel contentContainer)
    {
        this.contentContainer = contentContainer;
        tabbedContents = new HashMap<>();
    }

    public <T extends JPanel> void viewTab(Class<T> tab)
    {
        if (!tabbedContents.containsKey(tab))
        {
            try {
                var newTab = tab.getDeclaredConstructor().newInstance();
                tabbedContents.put(tab, newTab);
            } catch (Exception ex) {
                Logger.getLogger(TabViewSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        var tabToShow = tabbedContents.get(tab);
        
        showContent(tabToShow);
    }

    private void showContent(JPanel tabToShow)
    {
        tabToShow.setSize(contentContainer.getSize());
        tabToShow.repaint();
        tabToShow.setAlignmentX(0);

        contentContainer.removeAll();
        contentContainer.add(tabToShow);
        contentContainer.revalidate();
        contentContainer.repaint();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import spotfifai.view.MenuItemForm;

/**
 *
 * @author admin
 */
public final class TabViewSystem
{

    private JPanel tabContainer;
    private JPanel contentContainer;
    private final List<MenuItemForm> menuItemList;
    MenuItemForm selectedMenuItem;
    private final Map<Class<?>, JPanel> tabbedContents;

    public TabViewSystem(JPanel tabContainer, JPanel contentContainer)
    {
        this.tabContainer = tabContainer;
        this.contentContainer = contentContainer;

        menuItemList = new ArrayList<>();
        tabbedContents = new HashMap<>();
    }

    public <T extends JPanel> T viewTab(Class<T> tab)
    {
        if (!tabbedContents.containsKey(tab))
        {
            try
            {
                var newTab = tab.getDeclaredConstructor().newInstance();
                tabbedContents.put(tab, newTab);
            } catch (Exception ex)
            {
                Logger.getLogger(TabViewSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        var tabToShow = tabbedContents.get(tab);

        showContent(tabToShow);
        return (T) tabToShow;
    }

    public <T extends JPanel> T viewTab(T tabToShow)
    {
        showContent(tabToShow);
        return (T) tabToShow;
    }

    public void addMenuItem(MenuItemForm menuItem)
    {
        menuItemList.add(menuItem);
        tabContainer.add(menuItem);
        
        menuItem.setOnSelected(() ->
        {
            setSelect(menuItem);
        });

        tabContainer.revalidate();
        tabContainer.repaint();
    }

    public void removeMenuItem(MenuItemForm menuItem)
    {
        menuItemList.remove(menuItem);
        tabContainer.remove(menuItem);
        if (!menuItemList.isEmpty())
        {
            MenuItemForm item = menuItemList.get(0);
            setSelect(item);
            showContent(item.getContentTab());
        }

        tabContainer.revalidate();
        tabContainer.repaint();
    }

    public void setSelect(MenuItemForm menuItem)
    {
        for (var item : menuItemList)
        {
            item.setSelection(false);
        }

        menuItem.setSelection(true);
        selectedMenuItem = menuItem;
    }

    private void showContent(JPanel tabToShow)
    {
        tabToShow.setSize(contentContainer.getSize());

        contentContainer.removeAll();
        contentContainer.add(tabToShow);
        tabToShow.revalidate();
        tabToShow.repaint();
        contentContainer.revalidate();
        contentContainer.repaint();
    }
}

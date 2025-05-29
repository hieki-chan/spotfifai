/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.util;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author admin
 */
public class SpotfifaiDialog
{
    protected JPanel panel;
    final JDialog dialog;
    final Window owner;
    
    public SpotfifaiDialog(String title, Window owner, JPanel panelForm)
    {
        this.owner = owner;
        dialog = new JDialog(owner, title, Dialog.ModalityType.APPLICATION_MODAL);

        panel = panelForm;
        dialog.add(panel);
    }
    
    public static SpotfifaiDialog show(String title, JPanel currentPanel, JPanel panelForm)
    {
        return show(title, SwingUtilities.getWindowAncestor(currentPanel), panelForm);
    }
    
    public static SpotfifaiDialog show(String title, Window owner, JPanel panelForm)
    {
        SpotfifaiDialog d = new SpotfifaiDialog(title, owner, panelForm);
        d.show();
        return d;
    }

    public void show()
    {
        dialog.setSize(getDialogSize());
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    protected Dimension getDialogSize()
    {
        return new Dimension(400, 500);
    }
}

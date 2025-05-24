/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.ui.songeditor;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import spotfifai.models.Song;

/**
 *
 * @author admin
 */
public class SongEditorDialog
{
    protected SongEditorForm editorForm;
    final JDialog dialog;
    final Window owner;

    public SongEditorDialog(String title, JPanel currentPanel, Song song)
    {
        owner = SwingUtilities.getWindowAncestor(currentPanel);
        dialog = new JDialog(owner, title, Dialog.ModalityType.APPLICATION_MODAL);

        editorForm = new SongEditorForm(song);
        dialog.add(editorForm);
    }

    public void show()
    {
        dialog.setSize(getDialogSize());
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
    }

    protected Dimension getDialogSize()
    {
        return new Dimension(400, 500);
    }
}

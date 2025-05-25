/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.ui;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 *
 * @author admin
 */
class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean clicked;
    private int row;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);

       
        button.addActionListener(e -> {
            fireEditingStopped(); 
            System.out.println("clicked at row: " + row);
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.row = row;
        label = (value == null) ? "Play" : value.toString();
        button.setText(label);
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        clicked = false;
        return super.stopCellEditing();
    }
}


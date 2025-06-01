/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.view;

import java.awt.Component;
import java.util.function.Consumer;
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

    public ButtonEditor(JTable table, Consumer<Integer> action) {
        super(new JCheckBox());
        button = new JButton();
        button.setOpaque(true);
       
        button.addActionListener(e -> {
            fireEditingStopped(); 
            action.accept(row);
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.row = row;
        String _label = (value == null) ? label : value.toString();
        //button.setText(value.toString());
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


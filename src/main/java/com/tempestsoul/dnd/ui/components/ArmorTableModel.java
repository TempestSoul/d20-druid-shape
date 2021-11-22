package com.tempestsoul.dnd.ui.components;

import com.tempestsoul.dnd.d20.model.Creature;

import javax.swing.table.AbstractTableModel;

public class ArmorTableModel extends AbstractTableModel {

    private final String[] columnNames = {"AC", "touch", "flat"};

    private Creature creature;

    public ArmorTableModel(Creature creature) {
        this.creature = creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
        case 0:
            return creature == null ? null : creature.getArmorCount();
        case 1:
            return creature == null ? null : creature.getTouchArmorCount();
        case 2:
            return creature == null ? null : creature.getFlatArmorCount();
        default:
            return null;
        }
    }
}

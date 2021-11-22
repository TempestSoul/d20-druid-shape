package com.tempestsoul.dnd.ui.components;

import com.tempestsoul.dnd.d20.model.Attack;
import com.tempestsoul.dnd.d20.model.Creature;

import javax.swing.table.AbstractTableModel;

public class AttacksTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Primary", "Name", "Atk Bonus", "Dmg"};

    private Creature creature;

    public AttacksTableModel(Creature creature) {
        this.creature = creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return creature == null ? 0 : creature.getAttacks().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Attack atk = creature.getAttacks().get(rowIndex);
        String value = null;
        switch(columnIndex) {
            case 0:
                value = atk.isPrimaryAtk() ? "P" : "S";
                break;
            case 1:
                value = (atk.getNumber() > 1 ? atk.getNumber() + " " : "") + atk.getName();
                break;
            case 2:
                value = String.format("%+d", creature.getAttackBonus(atk));
                break;
            case 3:
                value = atk.getDmgDie() + String.format("%+d", creature.getDamageBonus(atk));
                break;
            default: break;
        }
        return value;
    }
}

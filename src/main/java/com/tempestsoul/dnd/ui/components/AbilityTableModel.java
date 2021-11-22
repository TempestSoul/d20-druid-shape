package com.tempestsoul.dnd.ui.components;

import com.tempestsoul.dnd.d20.model.Ability;
import com.tempestsoul.dnd.d20.model.Creature;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.List;

public class AbilityTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Ability", "Score", "Ability", "Score"};
    private List<Ability> abilities = Arrays.asList(Ability.values());

    private Creature creature;

    public AbilityTableModel(Creature creature) {
        this.creature = creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return abilities.size() / 2;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        int arrayIndex = (columnIndex / 2) * getRowCount() + rowIndex;  // integer division means != columnIndex + rowIndex
        if (columnIndex % 2 == 1) {
            return creature == null ? null : creature.getStats().get(abilities.get(arrayIndex));
        } else {
            return abilities.get(arrayIndex);
        }
    }
}

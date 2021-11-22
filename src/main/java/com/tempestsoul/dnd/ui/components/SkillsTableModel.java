package com.tempestsoul.dnd.ui.components;

import com.tempestsoul.dnd.d20.model.Creature;
import com.tempestsoul.dnd.d20.model.Skill;

import javax.swing.table.AbstractTableModel;

public class SkillsTableModel extends AbstractTableModel {

    private final String[] columnNames = {"Skill", "Ability", "Modifier"};

    private Creature creature;

    public SkillsTableModel(Creature creature) {
        this.creature = creature;
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
        fireTableDataChanged();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public int getRowCount() {
        return creature == null ? 0 : creature.getSkills().size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (creature == null) return null;
        Skill skill = creature.getSkills().get(rowIndex);
        switch(columnIndex) {
            case 0:
                return skill.getName();
            case 1: return skill.getBaseAbility();
            case 2: return String.format("%+d", creature.getSkillMod(skill.getName()));
            default:
                return null;
        }
    }
}

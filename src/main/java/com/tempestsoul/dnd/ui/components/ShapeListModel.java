package com.tempestsoul.dnd.ui.components;

import com.tempestsoul.dnd.d20.model.Creature;

import javax.swing.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ShapeListModel extends AbstractListModel<Creature> {

    private final List<Creature> shapes;

    private List<Creature> filteredShapes;

    public ShapeListModel(List<Creature> shapes) {
        this.shapes = shapes;
        this.filteredShapes = shapes;
    }

    public void filter(Predicate<Creature> filter) {
        int end = filteredShapes.size() - 1;
        filteredShapes = shapes.stream().filter(filter).collect(Collectors.toList());
    }

    @Override
    public int getSize() {
        return filteredShapes.size();
    }

    @Override
    public Creature getElementAt(int index) {
        return filteredShapes.get(index);
    }


}

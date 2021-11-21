package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.Collection;
import java.util.LinkedList;

public abstract class RestrictiveCell extends Cell {

    protected final Collection<Class<? extends Entity>> restrictiveList;

    /**
     *
     * @param x
     * @param y
     * @param sprite
     * @param restrictiveList
     */
    public RestrictiveCell(int x, int y, Sprite sprite, Collection<Class<? extends Entity>> restrictiveList) {
        super(x, y, sprite);
        this.restrictiveList = restrictiveList;
    }
}

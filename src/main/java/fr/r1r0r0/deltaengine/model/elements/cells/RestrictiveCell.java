package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A restrictive version of the Cell, who can accept or not given entities who want to cross it.
 */
public abstract class RestrictiveCell extends Cell {

    /**
     * Restrictive list of Entities class. Could be used to parameter who can cross or not this cell.
     */
    protected final Collection<Class<? extends Entity>> restrictiveList;

    /**
     * Default constructor. Set a case sprite and coordinates in the map, and a collection of entities' class.
     * @param x the abscissa coordinate of the cell in the map. Coordinates values are integers.
     * @param y the ordinate coordinate of the cell in the map. Coordinates values are integers.
     * @param restrictiveList Collection of entity class
     * @param sprite the sprite to apply on the case
     */
    public RestrictiveCell(int x, int y, Sprite sprite, Collection<Class<? extends Entity>> restrictiveList) {
        super(x, y, sprite);
        this.restrictiveList = restrictiveList;
    }
}

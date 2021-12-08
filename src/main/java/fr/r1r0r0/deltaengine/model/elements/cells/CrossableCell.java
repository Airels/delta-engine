package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

/**
 * A default crossable cell. Any entity can cross this cell.
 * Useful when you want to create a cell who anybody can cross it. Like paths.
 */
public class CrossableCell extends Cell {

    /**
     * Default constructor. Set a case sprite and coordinates in the map.
     *
     * @param x      the abscissa coordinate of the cell in the map. Coordinates values are integers.
     * @param y      the ordinate coordinate of the cell in the map. Coordinates values are integers.
     * @param sprite the sprite to apply on the case
     */
    public CrossableCell(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public final boolean isCrossableBy(Entity entity) {
        return true;
    }
}

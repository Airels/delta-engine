package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

/**
 * A default uncrossable cell. Any entity cannot cross this cell.
 * Useful when you want to create a cell who nobody can cross it. Like walls.
 */
public class UncrossableCell extends Cell {

    /**
     * Default constructor. Set a case sprite and coordinates in the map.
     *
     * @param x      the abscissa coordinate of the cell in the map. Coordinates values are integers.
     * @param y      the ordinate coordinate of the cell in the map. Coordinates values are integers.
     * @param sprite the sprite to apply on the case
     */
    public UncrossableCell(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public final boolean isCrossableBy(Entity entity) {
        return false;
    }
}

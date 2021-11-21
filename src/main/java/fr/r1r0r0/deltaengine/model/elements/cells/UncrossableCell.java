package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

public class UncrossableCell extends Cell {
    /**
     * Default constructor. Set a case sprite and coordinates in the map.
     *
     * @param x      the coordinates of the map. Coordinates values are integers. If doubles, it will be truncated.
     * @param y
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

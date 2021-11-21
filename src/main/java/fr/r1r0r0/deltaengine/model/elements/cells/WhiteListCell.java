package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.Collection;

/**
 * A specific RestrictiveCell, who only allows every given entities to cross it.
 * If entity class was not given, it will refuse to be crossed by it.
 */
public class WhiteListCell extends RestrictiveCell {

    /**
     * Default constructor. Set a case sprite and coordinates in the map, and a collection of entities' class.
     * @param x the abscissa coordinate of the cell in the map. Coordinates values are integers.
     * @param y the ordinate coordinate of the cell in the map. Coordinates values are integers.
     * @param restrictiveList Collection of entities allowed to cross this cell
     * @param sprite the sprite to apply on the case
     */
    public WhiteListCell(int x, int y, Sprite sprite, Collection<Class<? extends Entity>> restrictiveList) {
        super(x, y, sprite, restrictiveList);
    }

    @Override
    public final boolean isCrossableBy(Entity entity) {
        return restrictiveList.contains(entity.getClass());
    }
}

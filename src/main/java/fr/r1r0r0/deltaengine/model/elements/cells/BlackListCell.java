package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.Collection;

/**
 * A specific RestrictiveCell, who will deny every given entities to cross it.
 * If entity class was not given, it will allow to be crossed by it.
 */
public class BlackListCell extends RestrictiveCell {

    /**
     * Default constructor. Set a case sprite and coordinates in the map.
     * @param x the abscissa coordinate of the cell in the map. Coordinates values are integers.
     * @param y the ordinate coordinate of the cell in the map. Coordinates values are integers.
     * @param sprite the sprite to apply on the case
     */
    public BlackListCell(int x, int y, Sprite sprite, Collection<Class<? extends Entity>> restrictiveList) {
        super(x, y, sprite, restrictiveList);
    }

    @Override
    public final boolean isCrossableBy(Entity entity) {
        return !restrictiveList.contains(entity.getClass());
    }
}

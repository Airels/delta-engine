package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.Collection;

public class WhiteListCell extends RestrictiveCell {
    /**
     * @param x
     * @param y
     * @param sprite
     * @param restrictiveList
     */
    public WhiteListCell(int x, int y, Sprite sprite, Collection<Class<? extends Entity>> restrictiveList) {
        super(x, y, sprite, restrictiveList);
    }

    @Override
    public final boolean isCrossableBy(Entity entity) {
        return restrictiveList.contains(entity.getClass());
    }
}

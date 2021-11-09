package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * A specific Cell in a Grid. An Uncrossable Cell, like walls or doors.
 * Can be parametrized to be crossed by specified entities.
 */
public class UncrossableCell extends Cell {

    private List<Class<? extends Entity>> authorizedEntitiesToCross;

    public UncrossableCell(int x, int y, Sprite sprite) {
        super(x, y, sprite);

        authorizedEntitiesToCross = new ArrayList<>();
    }

    /**
     * Check if an entity con cross this Cell
     * @param entity entity to check
     * @return true if entity can cross this, false otherwise
     */
    public boolean isCrossableBy(Entity entity) {
        return authorizedEntitiesToCross.contains(entity.getClass());
    }

    /**
     * Add what class child of Entity can cross this case, who technically can't be crossed.
     * @param allowedEntity Entity Class authorized to cross this case.
     */
    public void canBeCrossedBy(Class<? extends Entity> allowedEntity) {
        authorizedEntitiesToCross.add(allowedEntity);
    }

    @Override
    final boolean isCrossable() {
        return false;
    }
}

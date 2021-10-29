package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * A specific Case in a Grid. An Uncrossable Case, like walls or doors.
 * Can be parametrized to be crossed by specified entities.
 */
public class UncrossableCase extends Case {

    private List<Class<? extends Entity>> authorizedEntitiesToCross;

    public UncrossableCase(int x, int y, Sprite sprite) {
        super(x, y, sprite);

        authorizedEntitiesToCross = new ArrayList<>();
    }

    /**
     * Check if an entity con cross this Case
     * @param entity entity to check
     * @return true if entity can cross this, false otherwise
     */
    public boolean isCrossableBy(Entity entity) {
        return authorizedEntitiesToCross.contains(entity.getClass());
    }

    public void canBeCrossedBy(Class<? extends Entity> allowedEntity) {
        authorizedEntitiesToCross.add(allowedEntity);
    }

    @Override
    final boolean isCrossable() {
        return false;
    }
}

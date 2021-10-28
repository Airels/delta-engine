package model.elements.static_elements;

import model.elements.entities.Entity;

public class UncrossableCase extends Case {
    public boolean isCrossableBy(Entity entity) {
        return false;
    }

    public void canBeCrossedBy(Class<? extends Entity> allowedEntity) {

    }

    @Override
    boolean isCrossable() {
        return false;
    }
}

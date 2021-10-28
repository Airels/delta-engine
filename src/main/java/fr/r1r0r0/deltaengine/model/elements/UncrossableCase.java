package fr.r1r0r0.deltaengine.model.elements;

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

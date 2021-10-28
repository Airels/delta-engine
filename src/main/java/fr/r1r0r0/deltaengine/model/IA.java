package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.model.elements.Entity;

public abstract class IA {

    private Entity attachedEntity;

    public final void setEntity(Entity entity) {
        attachedEntity = entity;
    }

    public final Entity getEntity() {
        return attachedEntity;
    }

    public abstract void tick();
}

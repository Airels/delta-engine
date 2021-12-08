package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;

/**
 * An abstract class that defines an AI. An AI can be attached to an Entity
 * @see Entity
 */
public abstract class AI {

    private Entity attachedEntity;

    /**
     * To bind an entity, who is an AI can access and update its properties
     * @param entity entity to bind
     * @deprecated Do not call it manually. Its called automatically when you attach an AI to an entity.
     */
    public final void setEntity(Entity entity) {
        attachedEntity = entity;
    }

    /**
     * Get bound entity, to update its properties
     * @return bound entity
     */
    public final Entity getEntity() {
        return attachedEntity;
    }

    /**
     * Main loop of an AI, called by Engine to process its behaviour
     */
    public abstract void tick();
}

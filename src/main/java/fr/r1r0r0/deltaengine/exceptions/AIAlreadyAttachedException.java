package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;

/**
 * Exception thrown when trying to attach an AI to an entity, but an AI is already attached to it
 */
public class AIAlreadyAttachedException extends Exception {

    public AIAlreadyAttachedException(String msg) {
        super(msg);
    }

    public AIAlreadyAttachedException(Entity entity) {
        this(entity.getName() + " already have attached AI");
    }
}

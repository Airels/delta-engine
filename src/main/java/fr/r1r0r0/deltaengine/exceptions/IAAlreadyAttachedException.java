package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.elements.Entity;

public class IAAlreadyAttachedException extends Exception {

    public IAAlreadyAttachedException(String msg) {
        super(msg);
    }

    public IAAlreadyAttachedException(Entity entity) {
        this(entity.getName() + " already have attached AI");
    }
}

package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.model.elements.Entity;

public interface IA {

    void setEntity(Entity entity);
    Entity getEntity();
    void tick();
}

package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.elements.Entity;

public class MapEntityNameStackingException extends Exception {

    public MapEntityNameStackingException (String msg) {
        super(msg);
    }

    public MapEntityNameStackingException (Entity entity1, Entity entity2) {
        this("Map contain 2 entity with the same name : entity1:" + entity1 + ", entity2:" + entity2);
    }

}

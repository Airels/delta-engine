package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.elements.Entity;

public class MapEntityDuplicateException extends Exception {

    public MapEntityDuplicateException (String msg) {
        super(msg);
    }

    public MapEntityDuplicateException (Entity entity) {
        this("Map contain multiple time the entity : " + entity);
    }

}

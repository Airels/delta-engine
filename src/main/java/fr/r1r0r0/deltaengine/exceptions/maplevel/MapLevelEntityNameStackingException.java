package fr.r1r0r0.deltaengine.exceptions.maplevel;

import fr.r1r0r0.deltaengine.model.elements.Entity;

public class MapLevelEntityNameStackingException extends MapLevelException {

    public MapLevelEntityNameStackingException(String msg) {
        super(msg);
    }

    public MapLevelEntityNameStackingException(Entity entity1, Entity entity2) {
        this("MapLevel contain 2 entity with the same name : entity1:" + entity1 + ", entity2:" + entity2);
    }

}

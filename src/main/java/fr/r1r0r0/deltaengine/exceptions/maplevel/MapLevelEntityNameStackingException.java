package fr.r1r0r0.deltaengine.exceptions.maplevel;

import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;

/**
 * An exception throw when 2 entities with the same name are in the same MapLevel
 * @see MapLevelException
 */
public class MapLevelEntityNameStackingException extends MapLevelException {

    /**
     * Constructor
     * @param msg the message show in the exception
     */
    public MapLevelEntityNameStackingException(String msg) {
        super(msg);
    }

    /**
     * Constructor
     * @param mapLevel a mapLevel
     * @param entity1 an entity
     * @param entity2 an entity
     */
    public MapLevelEntityNameStackingException(MapLevel mapLevel, Entity entity1, Entity entity2) {
        this("MapLevel contain 2 entity with the same name : mapLevel_name=" + mapLevel.getName()
                + ", entity1=" + entity1 + ", entity2=" + entity2);
    }

}

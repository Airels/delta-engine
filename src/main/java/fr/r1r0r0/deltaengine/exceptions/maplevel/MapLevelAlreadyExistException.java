package fr.r1r0r0.deltaengine.exceptions.maplevel;

import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;

/**
 * An exception throw when 2 objects MapLevel with the same name are add in a Map<String,MapLevel>
 * where the String corresponding to the name of the MapLevel
 * @see MapLevelException
 */
public class MapLevelAlreadyExistException extends MapLevelException {

    /**
     * Constructor
     * @param msg the message show in the exception
     */
    public MapLevelAlreadyExistException(String msg) {
        super(msg);
    }

    /**
     * Constructor
     * @param mapLevel1 a mapLevel
     * @param mapLevel2 a mapLevel
     */
    public MapLevelAlreadyExistException(MapLevel mapLevel1, MapLevel mapLevel2) {
        this("There is 2 MapLevel with the same name + " + mapLevel1.getName() + " : mapLevel1=" + mapLevel1 +
                ", mapLevel2=" + mapLevel2);
    }

}

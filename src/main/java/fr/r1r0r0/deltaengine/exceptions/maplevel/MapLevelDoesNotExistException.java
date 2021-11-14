package fr.r1r0r0.deltaengine.exceptions.maplevel;

/**
 * An exception throw when there is not map matching with the name required in a Map<String,MapLevel> where
 * the String corresponding to the name of the MapLevel
 * @see MapLevelException
 */
public class MapLevelDoesNotExistException extends MapLevelException {

    /**
     * Constructor
     * @param name the name of the map
     */
    public MapLevelDoesNotExistException(String name) {
        super("MapLevel does not exist : name=" + name);
    }

}

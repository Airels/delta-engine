package fr.r1r0r0.deltaengine.exceptions.maplevel;

public class MapLevelDoesNotExistException extends MapLevelException {

    public MapLevelDoesNotExistException(String name) {
        super("MapLevel " + name + " does not exist");
    }
}

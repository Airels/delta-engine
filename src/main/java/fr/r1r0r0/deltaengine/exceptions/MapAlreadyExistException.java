package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.MapLevel;

public class MapAlreadyExistException extends Exception {

    public MapAlreadyExistException(String msg) {
        super(msg);
    }

    public MapAlreadyExistException(MapLevel mapLevel) {
        super("MapLevel with name " + mapLevel.getName() + " already exist");
    }
}

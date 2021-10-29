package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.Map;

public class MapAlreadyExistException extends Exception {

    public MapAlreadyExistException(String msg) {
        super(msg);
    }

    public MapAlreadyExistException(Map map) {
        super("Map with name " + map.getName() + " already exist");
    }
}

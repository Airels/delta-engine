package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.Map;

public class MapDoesNotExistException extends Exception {

    public MapDoesNotExistException(String name) {
        super("Map " + name + " does not exist");
    }
}

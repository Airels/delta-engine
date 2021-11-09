package fr.r1r0r0.deltaengine.exceptions;

public class MapDoesNotExistException extends Exception {

    public MapDoesNotExistException(String name) {
        super("MapLevel " + name + " does not exist");
    }
}

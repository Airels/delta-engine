package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.Coordinates;

public class MapOutOfBoundException extends Exception {

    public MapOutOfBoundException (String msg) {
        super(msg);
    }

    public MapOutOfBoundException (int width, int height, Coordinates coordinates) {
        this("Map size can not contain the coordinate : width=" + width + ", height=" + height
                + ", coordinate=" + coordinates);
    }

}

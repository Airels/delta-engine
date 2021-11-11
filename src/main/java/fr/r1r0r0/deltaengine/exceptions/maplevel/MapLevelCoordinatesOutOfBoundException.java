package fr.r1r0r0.deltaengine.exceptions.maplevel;

import fr.r1r0r0.deltaengine.model.Coordinates;

public class MapLevelCoordinatesOutOfBoundException extends MapLevelException {

    public MapLevelCoordinatesOutOfBoundException(String msg) {
        super(msg);
    }

    public MapLevelCoordinatesOutOfBoundException(int width, int height, Coordinates coordinates) {
        this("MapLevel size can not contain the coordinate : width=" + width + ", height=" + height
                + ", coordinate=" + coordinates);
    }

}

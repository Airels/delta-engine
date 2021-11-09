package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.elements.Cell;

public class MapCaseCoordinateStackingException extends Exception {

    public MapCaseCoordinateStackingException (String msg) {
        super(msg);
    }

    public MapCaseCoordinateStackingException (Cell c1, Cell c2) {
        this("MapLevel contain 2 case at the same coordinate : c1:" + c1 + ", c2:" + c2);
    }

}

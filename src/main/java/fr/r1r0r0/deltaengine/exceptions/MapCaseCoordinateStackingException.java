package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.elements.Case;

public class MapCaseCoordinateStackingException extends Exception {

    public MapCaseCoordinateStackingException (String msg) {
        super(msg);
    }

    public MapCaseCoordinateStackingException (Case c1, Case c2) {
        this("Map contain 2 case at the same coordinate : c1:" + c1 + ", c2:" + c2);
    }

}

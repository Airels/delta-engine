package fr.r1r0r0.deltaengine.exceptions.maplevel;

import fr.r1r0r0.deltaengine.model.elements.Cell;

public class MapLevelCellCoordinatesStackingException extends MapLevelException {

    public MapLevelCellCoordinatesStackingException(String msg) {
        super(msg);
    }

    public MapLevelCellCoordinatesStackingException(Cell c1, Cell c2) {
        this("MapLevel contain 2 case at the same coordinate : c1:" + c1 + ", c2:" + c2);
    }

}

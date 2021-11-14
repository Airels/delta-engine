package fr.r1r0r0.deltaengine.exceptions.maplevel;

import fr.r1r0r0.deltaengine.model.elements.Cell;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;

/**
 * An exception throw when a Cell is add in a MapLevel, but the Coordinates<Integer> of the cell are
 * out of bound of the area defined by the MapLevel
 * @see MapLevelException
 */
public class MapLevelCoordinatesOutOfBoundException extends MapLevelException {

    /**
     * Constructor
     * @param msg the message show in the exception
     */
    public MapLevelCoordinatesOutOfBoundException(String msg) {
        super(msg);
    }

    /**
     * Constructor
     * @param mapLevel a mapLevel
     * @param cell a cell
     */
    public MapLevelCoordinatesOutOfBoundException(MapLevel mapLevel, Cell cell) {
        this("MapLevel size can not contain the coordinate : width=" + mapLevel.getWidth()
                + ", height=" + mapLevel.getHeight() + ", cell=" + cell);
    }

}

package fr.r1r0r0.deltaengine.exceptions.maplevel;

import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;

/**
 * @see MapLevelException
 */
public class MapLevelBuilderCellCoordinatesStackingException extends MapLevelException {

    /**
     * Constructor
     * @param msg the message show in the exception
     */
    public MapLevelBuilderCellCoordinatesStackingException (String msg) {
        super(msg);
    }

    /**
     * Constructor
     * @param mapLevelBuilder a mapLevelBuilder
     * @param cell1 a cell
     * @param cell2 a cell
     */
    public MapLevelBuilderCellCoordinatesStackingException (MapLevelBuilder mapLevelBuilder, Cell cell1, Cell cell2) {
        this("MapLevelBuilder contain 2 cell with the same coordinates : mapLevelBuilder=" + mapLevelBuilder
                + ", cell1=" + cell1 + ", cell2=" + cell2);
    }

}

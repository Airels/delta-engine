package fr.r1r0r0.deltaengine.model.elements.cells;

import fr.r1r0r0.deltaengine.model.Builder;

/**
 * An interface used to create object Cell
 * Can be used like :
 *  - object.setX(x).setY(y).build()
 *  - object.build(x,y)
 */
public interface CellBuilder extends Builder<Cell> {

    /**
     * Setter for the value X
     * @param x the horizontal coordinate
     * @return the current object
     */
    CellBuilder setX (int x);

    /**
     * Setter for the value Y
     * @param y the vertical coordinate
     * @return the current object
     */
    CellBuilder setY (int y);

    /**
     * A method that build a Cell with the coordinate (x,y)
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     * @return a Cell
     */
    Cell build (int x, int y);

}

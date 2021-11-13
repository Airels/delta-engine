package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Builder;

/**
 * TODO
 */
public abstract class CellBuilder implements Builder<Cell> {

    /**
     * TODO
     */
    public CellBuilder () {}

    /**
     * TODO
     * @param x
     * @return
     */
    public abstract CellBuilder setX (int x);

    /**
     * TODO
     * @param y
     * @return
     */
    public abstract CellBuilder setY (int y);

}

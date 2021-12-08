package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.model.elements.cells.CellBuilder;

/**
 * Interface used to create builder of an object
 * @param <E> teh type of the object created
 * @see fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder
 * @see CellBuilder
 */
public interface Builder <E> {

    /**
     * A method to build an object of type E
     * @return an object of type E
     */
    E build ();

}

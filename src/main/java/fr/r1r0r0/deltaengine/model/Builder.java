package fr.r1r0r0.deltaengine.model;

/**
 * Interface used to create builder of an object
 * @param <E> teh type of the object created
 */
public interface Builder <E> {

    /**
     * A method to build an object of type E
     * @return an object of type E
     */
    E build ();

}

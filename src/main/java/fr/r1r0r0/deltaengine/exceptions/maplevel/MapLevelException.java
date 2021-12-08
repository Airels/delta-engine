package fr.r1r0r0.deltaengine.exceptions.maplevel;

/**
 * A custom exception used to regroup all exception relative to class MapLevel
 */
public abstract class MapLevelException extends Exception {

    /**
     * Constructor
     * @param msg the message show in the exception
     */
    protected MapLevelException (String msg) {
        super(msg);
    }

}

package fr.r1r0r0.deltaengine.exceptions;

/**
 * Exception thrown when an object, class or anything is trying to be initialized, but it is already initialized
 */
public class AlreadyInitializedException extends Exception {

    public AlreadyInitializedException(String msg) {
        super(msg);
    }
}

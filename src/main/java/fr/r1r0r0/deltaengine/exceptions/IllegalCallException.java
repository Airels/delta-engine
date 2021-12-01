package fr.r1r0r0.deltaengine.exceptions;

/**
 * Exception thrown when a method should not be called, but called anyway
 */
public class IllegalCallException extends Exception {

    public IllegalCallException(String msg) {
        super(msg);
    }
}

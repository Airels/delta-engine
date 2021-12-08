package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.engines.Engines;

/**
 * Exception thrown when method, class or anything hasn't been initialized yet
 */
public class NotInitializedException extends Exception {

    public NotInitializedException(String str) {
        super(str);
    }

    public NotInitializedException(Engines engine) {
        this(engine.name() + " was not initialized yet. (Call init() first)");
    }
}

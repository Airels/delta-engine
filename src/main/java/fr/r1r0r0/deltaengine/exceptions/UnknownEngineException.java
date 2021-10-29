package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.engines.Engines;

public class UnknownEngineException extends Exception {

    public UnknownEngineException(String msg) {
        super(msg);
    }

    public UnknownEngineException(Engines engine) {
        this("Unknown engine " + engine);
    }
}

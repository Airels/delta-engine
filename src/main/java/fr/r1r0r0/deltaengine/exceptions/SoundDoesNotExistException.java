package fr.r1r0r0.deltaengine.exceptions;

public class SoundDoesNotExistException extends Exception {

    public SoundDoesNotExistException(String name) {
        super("Sound " + name + " does not exist");
    }
}

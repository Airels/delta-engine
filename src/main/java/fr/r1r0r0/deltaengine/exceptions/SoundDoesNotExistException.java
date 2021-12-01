package fr.r1r0r0.deltaengine.exceptions;

/**
 * Exception thrown when the user wants to play a music but the music isn't in the SoundEngine memory
 */
public class SoundDoesNotExistException extends Exception {

    public SoundDoesNotExistException(String name) {
        super("Sound " + name + " does not exist");
    }
}

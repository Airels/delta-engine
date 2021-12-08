package fr.r1r0r0.deltaengine.exceptions;

import fr.r1r0r0.deltaengine.model.Sound;

/**
 * Exception thrown when trying to add a new Sound into the SoundEngine memory,
 * but the Sound name already exist
 */
public class SoundAlreadyExistException extends Exception {

    public SoundAlreadyExistException(String msg) {
        super(msg);
    }

    public SoundAlreadyExistException(Sound sound) {
        super("Sound with name " + sound.getName() + " already exist");
    }
}

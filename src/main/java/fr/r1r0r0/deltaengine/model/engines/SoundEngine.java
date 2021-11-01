package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.exceptions.SoundAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.SoundDoesNotExistException;
import fr.r1r0r0.deltaengine.model.Sound;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Sound Engine, allows to control all given sounds.
 * @see Sound sounds objects manipulated by this Engine
 */
public final class SoundEngine implements Engine {

    private final Map<String, MediaPlayer> sounds;

    SoundEngine() {
        sounds = new HashMap<>();
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {

    }

    /**
     * Add a sound to the engine. Sound Name must be unique.
     * @param sound sound to add
     * @throws SoundAlreadyExistException exception thrown if sound name was already added previously
     */
    public void addSound(Sound sound) throws SoundAlreadyExistException {
        if (sounds.containsKey(sound.getName()))
            throw new SoundAlreadyExistException(sound);

        Media media = new Media(sound.getFile().toURI().toString());
        sounds.put(sound.getName(), new MediaPlayer(media));
    }

    /**
     * Remove a sound previously added from the engine.
     * @param name sound name to remove
     * @return true if removed, false otherwise (if sound doesn't exist)
     */
    public boolean removeSound(String name) {
        return sounds.remove(name) != null;
    }

    /**
     * Play a sound. Must be added before.
     * @param name sound name to play
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @see SoundEngine#addSound(Sound) to add a sound before playing it
     */
    public void play(String name) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        player.play();
    }

    /**
     * Pause a sound. Must be added before.
     * @param name sound name to pause
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @see SoundEngine#addSound(Sound) to add a sound before pause it
     */
    public void pause(String name) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        player.pause();
    }

    /**
     * Stop a sound. Must be added before.
     * @param name sound name to stop
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @see SoundEngine#addSound(Sound) to add a sound before stopping it
     */
    public void stop(String name) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        player.stop();
    }

    /**
     * Set volume to a specific sound. Must be added before.
     * @param name sound name to update volume
     * @param volume new sound volume, must be in range [0.0, 1.0]
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @throws IllegalArgumentException if volume value is outside authorized range
     * @see SoundEngine#addSound(Sound) to add a sound before updating its properties
     */
    public void setVolume(String name, double volume) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        if (volume < 0 || volume > 1) throw new IllegalArgumentException("Volume value must be in range [0.0, 1.0]");

        player.setVolume(volume);
    }

    /**
     * Set balance to a specific sound. Must be added before.
     * @param name sound name to update volume
     * @param balance new sound balance, must be in range [-1.0, 1.0]
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @throws IllegalArgumentException if volume balance is outside authorized range
     * @see SoundEngine#addSound(Sound) to add a sound before updating its properties
     */
    public void setBalance(String name, double balance) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        if (balance < -1 || balance > 1) throw new IllegalArgumentException("Balance value must be in range [-1.0, 1.0]");
        player.setBalance(balance);
    }

    /**
     * Set speed to a specific sound. Must be added before.
     * @param name sound name to update volume
     * @param speed new sound speed, must be in range [0.0, 8.0]
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @throws IllegalArgumentException if volume speed is outside authorized range
     * @see SoundEngine#addSound(Sound) to add a sound before updating its properties
     */
    public void setSpeed(String name, double speed) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        if (speed < 0 || speed > 8) throw new IllegalArgumentException("Speed value must be in range [0.0, 8.0]");

        player.setRate(speed);
    }

    /**
     * Allows mute or unmute specified music. Must be added before.
     * @param name sound name to mute/unmute
     * @param setMute boolean: true to mute, false to unmute
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @see SoundEngine#addSound(Sound) to add a sound before updating its properties
     */
    public void setMute(String name, boolean setMute) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        player.setMute(setMute);
    }

    /**
     * Allows looping or not specified music. Must be added before.
     * @param name sound name to loop (or not)
     * @param setLoop boolean: true to loop, false to not loop
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @see SoundEngine#addSound(Sound) to add a sound before updating its properties
     */
    public void setLoop(String name, boolean setLoop) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        int count = setLoop ? MediaPlayer.INDEFINITE : 1;
        player.setCycleCount(count);
    }

    /**
     * Gets associated music JavaFX Media Player, to have full customization
     * @param name sound name Media Player to get
     * @return MediaPlayer javafx object
     * @throws SoundDoesNotExistException if sound name is unknown for the engine
     * @see SoundEngine#addSound(Sound) to add a sound before trying to get its player
     */
    public MediaPlayer getMediaPlayer(String name) throws SoundDoesNotExistException {
        MediaPlayer player = sounds.get(name);
        if (player == null) throw new SoundDoesNotExistException(name);

        return player;
    }
}

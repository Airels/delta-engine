package fr.r1r0r0.deltaengine.model;

import java.io.File;

/**
 * A Sound class that contains audio clip.
 */
public final class Sound {

    private String name, path;
    private File file;
    private double volume;

    /**
     * Default constructor. Allows setting sound name and path to the audio clip
     *
     * @param name name of the audio
     * @param path path to the clip
     */
    public Sound(String name, String path) {
        this.name = name;
        this.path = path;
        volume = 1;
        file = new File(path);
    }

    /**
     * Returns current volume of audio clip
     *
     * @return double volume (0 minimum, 1 maximum)
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Get the name of audio clip
     *
     * @return String name of sound
     */
    public String getName() {
        return name;
    }

    /**
     * Get path of audio clip
     *
     * @return String path of audio clip
     */
    public String getPath() {
        return path;
    }

    /**
     * Get file descriptor for audio clip
     *
     * @return File audio clip file
     */
    public File getFile() {
        return file;
    }
}

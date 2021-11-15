package fr.r1r0r0.deltaengine.model;

import java.util.*;

/**
 * A custom HashMap that save element that was added and remove
 * @param <E> the type of the key
 * @param <F> the type of the value
 */
public class BufferedHashMap<E,F> extends HashMap<E,F> {

    private final Collection<F> previousAdd;
    private final Collection<F> previousRemove;

    /**
     * Constructor
     */
    public BufferedHashMap() {
        super();
        previousAdd = new Stack<>();
        previousRemove = new Stack<>();
    }

    /**
     * Add a couple key/value in the hashmap
     * Save the value in the collection of the attribute previousAdd
     * @param key a key
     * @param value a value
     * @return the previous value of the key
     */
    @Override
    public F put(E key, F value) {
        previousAdd.add(value);
        return super.put(key,value);
    }

    /**
     * Remove a key in the hashmap
     * Save the value in the collection of the attribute previousRemove
     * @param key a key
     * @return the value matching to the key that was removed
     */
    @Override
    public F remove(Object key) {
        F f = super.remove(key);
        previousRemove.add(f);
        return f;
    }

    /**
     * Clear the hashmap
     */
    @Override
    public void clear() {
        super.clear();
    }

    /**
     * Getter of the attribute previousAdd
     * @return the attribute previousAdd
     */
    public Collection<F> getPreviousAdd () {
        return previousAdd;
    }

    /**
     * Getter of the attribute previousRemove
     * @return the attribute previousRemove
     */
    public Collection<F> getPreviousRemove () {
        return previousRemove;
    }

    /**
     * Clear the attribute previousAdd
     */
    public void clearPreviousAdd () {
        previousAdd.clear();
    }

    /**
     * Clear the attribute previousRemove
     */
    public void clearPreviousRemove () {
        previousRemove.clear();
    }

}

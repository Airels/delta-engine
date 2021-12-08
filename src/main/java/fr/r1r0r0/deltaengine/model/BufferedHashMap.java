package fr.r1r0r0.deltaengine.model;

import java.util.*;

/**
 * A custom HashMap that save element that was added and remove
 * @param <E> the type of the key
 * @param <F> the type of the value
 */
public class BufferedHashMap<E,F> extends HashMap<E,F> {

    private Collection<F> previousAdd;
    private Collection<F> previousRemove;

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
        previousRemove.addAll(values());
        super.clear();
    }

    /**
     * Get elements previously added to the HashMap, and reset it for future usage
     * @return Collection of added elements
     */
    public Collection<F> getAndResetAddedElements() {
        try {
            return previousAdd;
        } finally {
            previousAdd = new Stack<>();
        }
    }

    /**
     * Get elements previously removed from the HashMap, and reset it for future usage
     * @return Collection of removed elements
     */
    public Collection<F> getAndResetRemovedElements() {
        try{
            return previousRemove;
        }finally {
            previousRemove = new Stack<>();
        }
    }



}

package fr.r1r0r0.deltaengine.model;

import java.util.*;

/**
 * TODO
 * @param <E>
 * @param <F>
 */
public class BufferedHashMap<E,F> extends HashMap<E,F> {

    private final Collection<F> previousAdd;
    private final Collection<F> previousRemove;

    /**
     * TODO
     */
    public BufferedHashMap() {
        previousAdd = new Stack<>();
        previousRemove = new Stack<>();
    }

    /**
     * TODO
     * @param key
     * @param value
     * @return
     */
    @Override
    public F put(E key, F value) {
        previousAdd.add(value);
        return super.put(key,value);
    }

    /**
     * TODO
     * @param key
     * @return
     */
    @Override
    public F remove(Object key) {
        F f = super.remove(key);
        previousRemove.add(f);
        return f;
    }

    /**
     * TODO
     */
    @Override
    public void clear() {
        super.clear();
    }

    /**
     * TODO
     * @return
     */
    public Collection<F> getPreviousAdd () {
        return previousAdd;
    }

    /**
     * TODO
     * @return
     */
    public Collection<F> getPreviousRemove () {
        return previousRemove;
    }

    /**
     * TODO
     */
    public void clearPreviousAdd () {
        previousAdd.clear();
    }

    /**
     * TODO
     */
    public void clearPreviousRemove () {
        previousRemove.clear();
    }

}

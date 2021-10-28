package fr.r1r0r0.deltaengine.model.events;

public interface Trigger<T> {

    void trigger(T param);
}

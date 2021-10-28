package model.events;

public interface Trigger<T> {

    void trigger(T param);
}

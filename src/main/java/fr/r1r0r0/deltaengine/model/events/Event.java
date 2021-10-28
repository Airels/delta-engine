package fr.r1r0r0.deltaengine.model.events;

public interface Event {

    void addTrigger(Trigger trigger);
    void removeTrigger(Trigger trigger);
    void clearTriggers();
    void checkEvent();
    void launchEvent();
}

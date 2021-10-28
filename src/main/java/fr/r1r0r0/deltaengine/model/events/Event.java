package fr.r1r0r0.deltaengine.model.events;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Event {

    private List<Trigger> triggers;

    public Event() {
        triggers = new LinkedList<>();
    }

    public final void addTrigger(Trigger trigger) {
        triggers.add(trigger);
    }

    public final boolean removeTrigger(Trigger trigger) {
        return triggers.remove(trigger);
    }

    public final void clearTriggers() {
        triggers.clear();
    }

    public final void runTriggers() {
        for (Trigger t : triggers)
            t.trigger();
    }

    public abstract void checkEvent();
}

package fr.r1r0r0.deltaengine.model.events;

public class VoidTrigger implements Trigger {

    public final static VoidTrigger INSTANCE = new VoidTrigger();

    private VoidTrigger() {

    }

    @Override
    public void trigger() {

    }
}

package fr.r1r0r0.deltaengine.model.events;

/**
 * A void event, that triggers nothing and checks nothing.
 * Used to avoid a null attribute.
 */
public class VoidTrigger implements Trigger {

    /**
     * Instance of this class, because it is impossible (and useless) to create a new one each time
     */
    public final static VoidTrigger INSTANCE = new VoidTrigger();

    /**
     * Cannot be instanced
     */
    private VoidTrigger() {

    }

    @Override
    public void trigger() {

    }

    /**
     * Instance of this class, because it is impossible (and useless) to create a new one each time
     * @return VoidTrigger instance
     */
    public static VoidTrigger getInstance() {
        return INSTANCE;
    }
}

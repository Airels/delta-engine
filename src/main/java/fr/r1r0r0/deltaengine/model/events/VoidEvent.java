package fr.r1r0r0.deltaengine.model.events;

/**
 * A void event, that triggers nothing and checks nothing.
 * Used to avoid a null attribute.
 */
public class VoidEvent extends Event {

    /**
     * Instance of this class, because it is impossible (and useless) to create a new one each time
     */
    public final static VoidEvent INSTANCE = new VoidEvent();

    /**
     * This class cannot be instancied
     */
    private VoidEvent() {

    }

    @Override
    public void checkEvent() {

    }

    /**
     * Instance of this class, because it is impossible (and useless) to create a new one each time
     * @return VoidEvent instance
     */
    public static VoidEvent getInstance() {
        return INSTANCE;
    }
}

package fr.r1r0r0.deltaengine.model.engines;

/**
 * All engines used by the Kernel Engine.
 * Contain instances of them.
 */
public enum Engines {
    INPUT_ENGINE(new InputEngine()),
    EVENT_ENGINE(new EventEngine()),
    AI_ENGINE(new AIEngine()),
    PHYSICS_ENGINE(new PhysicsEngine()),
    SOUND_ENGINE(new SoundEngine()),
    NETWORK_ENGINE(new NetworkEngine()),
    GRAPHICS_ENGINE(new GraphicsEngine());

    private final Engine engine;

    /**
     * Default constructor. Allows setting an instance of the specified Engine to be given later to
     * the Kernel.
     * @param engine Engine instance
     */
    Engines(Engine engine) {
        this.engine = engine;
    }

    /**
     * Gives an instance of the Engine
     * @return instance of the Engine
     */
    Engine getInstance() {
        return engine;
    }
}

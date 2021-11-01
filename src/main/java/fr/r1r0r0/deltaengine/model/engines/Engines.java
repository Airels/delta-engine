package fr.r1r0r0.deltaengine.model.engines;

public enum Engines {
    INPUT_ENGINE(new InputEngine()),
    EVENT_ENGINE(new EventEngine()),
    IA_ENGINE(new IAEngine()),
    PHYSICS_ENGINE(new PhysicsEngine()),
    SOUND_ENGINE(new SoundEngine()),
    NETWORK_ENGINE(new NetworkEngine()),
    GRAPHICS_ENGINE(new GraphicsEngine());

    private final Engine engine;

    Engines(Engine engine) {
        this.engine = engine;
    }

    Engine getInstance() {
        return engine;
    }
}

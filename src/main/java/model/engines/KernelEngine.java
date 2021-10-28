package model.engines;

import model.IA;
import model.elements.Element;
import model.elements.entities.Entity;
import model.engines.utils.Key;
import model.events.Event;

public class KernelEngine implements Engine {

    private InputEngine inputEngine;
    private IAEngine iaEngine;
    private PhysicsEngine physicsEngine;
    private EventEngine eventEngine;
    private GraphicsEngine graphicsEngine;
    private SoundEngine soundEngine;
    private NetworkEngine networkEngine;

    KernelEngine() {
        // TODO
    }

    @Override
    public void init() {
        inputEngine = new InputEngine();
        iaEngine = new IAEngine();
        // ...
    }

    @Override
    public void run() {

    }

    void getEngine(Engines engine) {

    }

    public void addElement(Element element) {
        graphicsEngine.addElement(element);

        if (element instanceof Entity) // TODO PB DE CONCEPTION
            physicsEngine.addEntity(((Entity) element));
    }

    public void removeElement(Element element) {

    }

    public void clearElements() {

    }

    public void setInput(Key key, Event event) {

    }

    public void addIA(IA ia) {

    }

    public void removeIA(IA ia) {

    }

    public void clearIAs() {

    }

    public void addEvent(Event event) {

    }

    public void removeEvent(Event event) {

    }

    public void clearEvents() {

    }

    public NetworkEngine getNetwork() {
        return networkEngine;
    }

    public SoundEngine getSoundEngine() {
        return soundEngine;
    }
}

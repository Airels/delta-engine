package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.IA;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.elements.ElementVisitor;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.Event;

public class KernelEngine extends Thread {

    private InputEngine inputEngine;
    private IAEngine iaEngine;
    private PhysicsEngine physicsEngine;
    private EventEngine eventEngine;
    private GraphicsEngine graphicsEngine;
    private SoundEngine soundEngine;
    private NetworkEngine networkEngine;

    public KernelEngine() {
        inputEngine = new InputEngine();
        iaEngine = new IAEngine();
        physicsEngine = new PhysicsEngine();
        // TODO ..
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public void run() {

    }

    void getEngine(Engines engine) {

    }

    public void addElement(Element element) {
        graphicsEngine.addElement(element);

        if (ElementVisitor.isAnEntity(element)) {
            Entity entity = (Entity) element;
            physicsEngine.addEntity(entity);

            if (entity.getIA() != null) {
                iaEngine.addIA(entity.getIA());
            }
        }
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

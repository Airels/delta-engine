package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.IA;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.elements.ElementVisitor;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
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

    public NetworkEngine getNetworkEngine() {
        return networkEngine;
    }

    public SoundEngine getSoundEngine() {
        return soundEngine;
    }

    public void addMap(Map map) {

    }

    public void removeMap(String name) {

    }

    public void clearMaps() {

    }

    public String getCurrentMapName() {
        return null;
    }

    public void setCurrentMap(String name) {

    }

    public void haltCurrentMap() {

    }

    public void resumeCurrentMap() {

    }

    public void setInput(Key key, Event event) {

    }

    public void clearInput(Key key) {

    }

    public void addGlobalEvent(Event event) {

    }

    public void removeGlobalEvent(Event event) {

    }

    public void clearGlobalEvents() {

    }

    public void addHUDElement(HUDElement hudElement) {

    }

    public void removeHUDElement(HUDElement hudElement) {

    }

    public void clearHUDElements() {

    }

    private void addElement(Element element) {
        graphicsEngine.addElement(element);

        if (ElementVisitor.isAnEntity(element)) {
            Entity entity = (Entity) element;
            physicsEngine.addEntity(entity);

            if (entity.getIA() != null) {
                iaEngine.addIA(entity.getIA());
            }
        }
    }

    private void removeElement(Element element) {

    }

    private void clearElements() {

    }

    private void addEvent(Event event) {

    }

    private void removeEvent(Event event) {

    }

    private void clearEvents() {

    }
}

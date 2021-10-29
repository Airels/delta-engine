package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.List;

public final class Map {

    private java.util.Map<Coordinates, Case> cases;

    public Map(String name, int width, int height) {

    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {

    }

    public void setCase(Case c) {

    }

    public Case getCase(int x, int y) {
        return null;
    }

    public void addEntity(Entity entity) {

    }

    public Entity getEntity(String name) {
        return null;
    }

    public void removeEntity(Entity entity) {

    }

    public void clearEntities() {

    }

    public void addEvent(Event event) {

    }

    public void removeEvent(Event event) {

    }

    public void clearEvents() {

    }

    public List<Case> getCases() {
        return null;
    }

    public List<Entity> getEntities() {
        return null;
    }

    public List<Event> getEvents() {
        return null;
    }
}

package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.exceptions.MapCaseCoordinateStackingException;
import fr.r1r0r0.deltaengine.exceptions.MapEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public final class Map {

    private final String name;
    private final int width;
    private final int height;
    private final java.util.Map<Coordinates,Case> cases;
    private final java.util.Map<String,Entity> entities;
    private final List<Event> events;

    public Map (String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        cases = new HashMap<>();
        entities = new HashMap<>();
        events = new ArrayList<>();
    }

    public String getName () {
        return name;
    }

    public int getWidth () {
        return width;
    }

    public int getHeight () {
        return height;
    }

    public void setCase (Case c) throws MapCaseCoordinateStackingException {
        Coordinates coordinates = c.getCoordinates();
        if (cases.containsKey(coordinates))
            throw new MapCaseCoordinateStackingException(cases.get(coordinates),c);
        cases.put(coordinates,c);
    }

    public Case getCase (int x, int y) {
        return cases.get(new Coordinates(x,y));
    }

    public void addEntity (Entity entity) throws MapEntityNameStackingException {
        if (entities.containsKey(entity.getName()) && entities.get(entity.getName()) != entity)
            throw new MapEntityNameStackingException(entities.get(entity.getName()),entity);
        entities.put(entity.getName(),entity);
    }

    public Entity getEntity (String name) {
        return entities.get(name);
    }

    public void removeEntity (Entity entity) {
        entities.remove(entity.getName());
    }

    public void clearEntities () {
        entities.clear();
    }

    public void addEvent (Event event) {
        if ( ! events.contains(event)) events.add(event);
    }

    public void removeEvent (Event event) {
        events.remove(event);
    }

    public void clearEvents () {
        events.clear();
    }

    public Collection<Case> getCases () {
        return cases.values();
    }

    public Collection<Entity> getEntities () {
        return entities.values();
    }

    public List<Event> getEvents () {
        return events;
    }

}

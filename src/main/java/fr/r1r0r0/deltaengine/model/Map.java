package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.exceptions.MapCaseCoordinateStackingException;
import fr.r1r0r0.deltaengine.exceptions.MapEntityNameStackingException;
import fr.r1r0r0.deltaengine.exceptions.MapOutOfBoundException;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.*;

/**
 * Map that represent the area where entities move
 */
public final class Map {

    private final String name;
    private final int width;
    private final int height;
    private final java.util.Map<Coordinates,Case> cases;
    private final java.util.Map<String,Entity> entities;
    private final LinkedHashSet<Event> events;

    /**
     * Constructor
     * @param name the name of the map
     * @param width the width of the map
     * @param height the height of the area
     */
    public Map (String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        cases = new HashMap<>();
        entities = new HashMap<>();
        events = new LinkedHashSet<>();
    }

    /**
     * Return the name
     * @return the name
     */
    public String getName () {
        return name;
    }

    /**
     * Return the width
     * @return the width
     */
    public int getWidth () {
        return width;
    }

    /**
     * Return the height
     * @return the height
     */
    public int getHeight () {
        return height;
    }

    //TODO: rename an addCase ?
    /**
     * Add a case in the map
     * @param c a case
     * @throws MapCaseCoordinateStackingException throw if a case with the same coordinate is already
     * present in the map
     * @throws MapOutOfBoundException throw if a case with coordinate out of the area define by the
     * map is add
     */
    public void setCase (Case c) throws MapCaseCoordinateStackingException, MapOutOfBoundException {
        Coordinates coordinates = c.getCoordinates();
        if (cases.containsKey(coordinates) && cases.get(coordinates) != c)
            throw new MapCaseCoordinateStackingException(cases.get(coordinates),c);
        if (coordinates.getX() < 0
                || coordinates.getX() >= width
                || coordinates.getY() < 0
                || coordinates.getY() >= height) throw new MapOutOfBoundException(width,height,coordinates);
        cases.put(coordinates,c);
    }

    /**
     * Return the case in the map with coordinate (x,y)
     * @param x the x value
     * @param y the y value
     * @return the case in the map with coordinate (x,y), or null if map does not contain case with this coordinate
     */
    public Case getCase (int x, int y) {
        return cases.get(new Coordinates(x,y));
    }

    /**
     * Add an entity in the map
     * @param entity an entity
     * @throws MapEntityNameStackingException throw if the name of the entity is already present in the map
     */
    public void addEntity (Entity entity) throws MapEntityNameStackingException {
        if (entities.containsKey(entity.getName()) && entities.get(entity.getName()) != entity)
            throw new MapEntityNameStackingException(entities.get(entity.getName()),entity);
        entities.put(entity.getName(),entity);
    }

    /**
     * Return the entity in map with the same given
     * @param name a name
     * @return an entity in map with the same given, or null if their is no entity with this name
     */
    public Entity getEntity (String name) {
        return entities.get(name);
    }

    /**
     * Remove an entity in map with the same given
     * @param entity an entity
     */
    public void removeEntity (Entity entity) {
        entities.remove(entity.getName());
    }

    /**
     * Remove all the entities present in map
     */
    public void clearEntities () {
        entities.clear();
    }

    /**
     * Add an event
     * @param event an event
     */
    public void addEvent (Event event) {
        events.add(event);
    }

    /**
     * Remove an event
     * @param event an event
     */
    public void removeEvent (Event event) {
        events.remove(event);
    }

    /**
     * Remove all events in map
     */
    public void clearEvents () {
        events.clear();
    }

    /**
     * Return all the cases in map
     * @return a collection of case
     */
    public Collection<Case> getCases () {
        return cases.values();
    }

    /**
     * Return all the entities in map
     * @return a collection of entity
     */
    public Collection<Entity> getEntities () {
        return entities.values();
    }

    /**
     * Return all the events in map
     * @return a collection of event
     */
    public Collection<Event> getEvents () {
        return events;
    }

}

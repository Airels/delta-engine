package fr.r1r0r0.deltaengine.model;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelCellCoordinatesStackingException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelCoordinatesOutOfBoundException;
import fr.r1r0r0.deltaengine.model.elements.Cell;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.elements.basic_cases.VoidCell;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.*;

/**
 * MapLevel that represent the area where entities move
 */
public final class MapLevel {

    private final String name;
    private final int width;
    private final int height;
    private final java.util.Map<Coordinates, Cell> cases;
    private final java.util.Map<String,Entity> entities;
    private final Set<Event> events;

    /**
     * Constructor
     * @param name the name of the map
     * @param width the width of the map
     * @param height the height of the area
     */
    public MapLevel(String name, int width, int height) {
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

    /**
     * Add a case in the map
     * @param c a case
     * @throws MapLevelCellCoordinatesStackingException throw if a case with the same coordinate is already
     * present in the map
     * @throws MapLevelCoordinatesOutOfBoundException throw if a case with coordinate out of the area define by the
     * map is added
     */
    public void addCase (Cell c) throws MapLevelCellCoordinatesStackingException, MapLevelCoordinatesOutOfBoundException {
        Coordinates coordinates = c.getCoordinates();
        if (cases.containsKey(coordinates) && cases.get(coordinates) != c)
            throw new MapLevelCellCoordinatesStackingException(cases.get(coordinates),c);
        if (coordinates.getX() < 0
                || coordinates.getX() >= width
                || coordinates.getY() < 0
                || coordinates.getY() >= height) throw new MapLevelCoordinatesOutOfBoundException(width,height,coordinates);
        cases.put(coordinates,c);
    }

    /**
     * Return the case in the map with coordinate (x,y)
     * @param x the x value
     * @param y the y value
     * @return the case in the map with coordinate (x,y),or a new VoidCell(x,y)
     * if map does not contain case with this coordinate
     */
    public Cell getCase (int x, int y) {
        Cell c = cases.get(new Coordinates(x,y));
        return (c == null) ? new VoidCell(x,y) : c;
    }

    /**
     * Add an entity in the map
     * @param entity an entity
     * @throws MapLevelEntityNameStackingException throw if the name of the entity is already present in the map
     */
    public void addEntity (Entity entity) throws MapLevelEntityNameStackingException {
        String name = entity.getName();
        if (entities.containsKey(name) && entities.get(name) != entity)
            throw new MapLevelEntityNameStackingException(entities.get(name),entity);
        entities.put(name,entity);
    }

    /**
     * Return the entity in map with the same given
     * @param name a name
     * @return an entity in map with the same given, or null if there is no entity with this name
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
    public Collection<Cell> getCases () {
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

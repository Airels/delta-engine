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
    private final java.util.Map<Coordinates,Cell> cells;
    private final java.util.Map<String,Entity> entities;
    private final Set<Event> events;

    /**
     * Constructor
     * @param name the name of the map
     * @param width the width of the map
     * @param height the height of the area
     */
    public MapLevel (String name, int width, int height) {
        this.name = name;
        this.width = width;
        this.height = height;
        cells = new HashMap<>();
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
     * @param cell a cell
     * @throws MapLevelCellCoordinatesStackingException throw if a cell with the same coordinate is already
     * present in the map
     * @throws MapLevelCoordinatesOutOfBoundException throw if a cell with coordinate out of the area define by the
     * map is added
     */
    public void addCase (Cell cell) throws MapLevelCellCoordinatesStackingException, MapLevelCoordinatesOutOfBoundException {
        Coordinates coordinates = cell.getCoordinates();
        if (cells.containsKey(coordinates) && cells.get(coordinates) != cell)
            throw new MapLevelCellCoordinatesStackingException(cells.get(coordinates),cell);
        if (coordinates.getX() < 0
                || coordinates.getX() >= width
                || coordinates.getY() < 0
                || coordinates.getY() >= height) throw new MapLevelCoordinatesOutOfBoundException(width,height,coordinates);
        cells.put(coordinates,cell);
    }

    /**
     * Return the cell in the map with coordinate (x,y)
     * @param x the x value
     * @param y the y value
     * @return the cell in the map with coordinate (x,y),or a new VoidCell(x,y)
     * if map does not contain cell with this coordinate
     */
    public Cell getCell (int x, int y) {
        Cell cell = cells.get(new Coordinates(x,y));
        return (cell == null) ? new VoidCell(x,y) : cell;
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
     * Return all the cells in map
     * @return a collection of case
     */
    public Collection<Cell> getCells () {
        return cells.values();
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

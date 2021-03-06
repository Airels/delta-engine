package fr.r1r0r0.deltaengine.model.maplevel;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelCoordinatesOutOfBoundException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelException;
import fr.r1r0r0.deltaengine.model.BufferedHashMap;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.default_cells.OutOfBoundCell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

/**
 * MapLevel that represent the area where entities move
 * It is compose of cells, entities and events
 * @see MapLevelBuilder
 */
public final class MapLevel {

    private final String name;
    private final int width;
    private final int height;
    private final BufferedHashMap<Coordinates<Integer>,Cell> cells;
    private final BufferedHashMap<String,Entity> entities;
    private final Collection<Event> events;

    /**
     * Constructor
     * @param name name of the map
     * @param cells cells that compose the map, can not contain null
     */
    MapLevel (String name, Cell[][] cells) {
        this.name = name;
        this.width = cells.length;
        this.height = (cells.length != 0) ? cells[0].length : 0;
        this.cells = new BufferedHashMap<>();
        entities = new BufferedHashMap<>();
        events = new ConcurrentLinkedDeque<>();
        try {
            for (Cell[] cells1 : cells) {
                for (Cell cell : cells1) {
                    if (cell == null)
                        throw new IllegalArgumentException("Argument Cells can not contain null");
                    replaceCell(cell);
                }
            }
        } catch (MapLevelException e) {
            //e.printStackTrace();
        }
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
     * @throws MapLevelCoordinatesOutOfBoundException throw if a cell with coordinate out of the area define by the
     * map is added
     */
    public void replaceCell (Cell cell) throws MapLevelCoordinatesOutOfBoundException {
        Coordinates<Integer> coordinates = cell.getCoordinates();
        if ( ! isValidCoordinate(coordinates))
            throw new MapLevelCoordinatesOutOfBoundException(this,cell);
        cells.put(coordinates,cell);
    }

    /**
     * Return if the coordinates given are include in the area of the map
     * @param coordinates a coordinate
     * @return if the coordinates are valid (include in the area of the map)
     */
    private boolean isValidCoordinate (Coordinates<Integer> coordinates) {
        return 0 <= coordinates.getX() && coordinates.getX() < width
                && 0 <= coordinates.getY() && coordinates.getY() < height;
    }

    /**
     * Return the cell in the map with coordinate (x,y)
     * @param x the x value
     * @param y the y value
     * @return the cell in the map with coordinate (x,y), returns OutOfBoundsCell if wanted cell is out of bounds
     */
    public Cell getCell (int x, int y) {
        Cell cell = cells.get(new Coordinates<>(x,y));
        return (cell == null) ? new OutOfBoundCell(x,y) : cell;
    }

    /**
     * Add an entity in the map
     * @param entity an entity
     * @throws MapLevelEntityNameStackingException throw if the name of the entity is already present in the map
     */
    public void addEntity (Entity entity) throws MapLevelEntityNameStackingException {
        String name = entity.getName();
        if (entities.containsKey(name) && entities.get(name) != entity)
            throw new MapLevelEntityNameStackingException(this,entities.get(name),entity);
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
     * add an event
     * @param event
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
     * @return a map of event
     */
    public Collection<Event> getEvents () {
        return events;
    }

    /**
     * Getter of attribute cells
     * @return attribute cells
     */
    public BufferedHashMap<Coordinates<Integer>,Cell> getBufferedCells () {
        return cells;
    }

    /**
     * Getter of attribute entities
     * @return attributes entities
     */
    public BufferedHashMap<String,Entity> getBufferedEntities () {
        return entities;
    }

}

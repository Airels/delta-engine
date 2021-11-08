package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.CrossableVisitor;
import fr.r1r0r0.deltaengine.model.elements.Entity;

/**
 * A physical engine
 */
class PhysicsEngine implements Engine {

    private Map map;
    private long previousRunTime;
    private long maxRunDelta;

    /**
     * Constructor
     */
    public PhysicsEngine () {
        map = null;
        previousRunTime = System.currentTimeMillis();
        maxRunDelta = 0;
    }

    /**
     * Initializer
     */
    @Override
    public void init() {

    }

    /**
     * Update the coordinate of all entity in the current map
     */
    @Override
    public void run() {
        long currentRunTime = System.currentTimeMillis();
        long deltaTime = previousRunTime - currentRunTime;
        deltaTime = Math.min(deltaTime, maxRunDelta);
        previousRunTime = currentRunTime;
        if (map != null) {
            for (Entity entity : map.getEntities()) {
                updateCoordinate(entity,deltaTime);
            }
        }
    }

    /**
     * Update the coordinate of an entity
     * @param entity an entity
     * @param deltaTime a interval of time used to calc the movement
     */
    private void updateCoordinate (Entity entity, long deltaTime) {
        Coordinates nextCoordinate = entity.getCoordinates().getNextCoordinates(entity.getDirection(),
                entity.getSpeed()*deltaTime);
        if (isValidCoordinates(nextCoordinate)) entity.setCoordinates(nextCoordinate);
        else entity.setDirection(Direction.IDLE);
    }

    /**
     * Returns if the coordinate is a valid coordinate in the map
     * @param coordinates a coordinate
     * @return if the coordinate is a valid coordinate in the map
     */
    private boolean isValidCoordinates (Coordinates coordinates) {
        int x = (coordinates.getX() >= 0) ? (int) coordinates.getX() : ((int) coordinates.getX() - 1);
        int y = (coordinates.getY() >= 0) ? (int) coordinates.getY() : ((int) coordinates.getY() - 1);
        return CrossableVisitor.isCaseCrossable(map.getCase(x,y));
    }

    /**
     * Replace the current map with another
     * @param map a map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Clear the current map
     */
    public void clearMap() {
        map = null;
    }

    public void setMaxRunDelta(int fps) {
        maxRunDelta = 1000 / fps;
    }
}

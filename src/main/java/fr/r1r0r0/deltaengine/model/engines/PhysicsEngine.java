package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.CrossableVisitor;
import fr.r1r0r0.deltaengine.model.elements.Entity;

/**
 * A physical engine
 * TODO: les marge d erreur deplacement pour les recentrer
 * TODO: prendre en compte les dimensions de l entite pour le deplacement
 * TODO: le points est topleft et prendre en compte les dimensions
 */
class PhysicsEngine implements Engine {

    /**
     * Explication de la mise en place des collisions entre une source et une target :
     * la source est une entitite qui posssede une liste de points de collision
     * qui par defaut correspondent aux points :
     * topleft, topright, botleft, botright, center, centerleft, centerright, centertop, centerbot
     * puis on va tester l appartenance d un de ces points dans la hitbox de la cible
     * qui correspond a un minX maxX minY maxY
     */

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
            for (Entity source : map.getEntities()) {
                for (Entity target : map.getEntities()) {
                    checkCollision(source,target);
                }
            }
        }
    }

    private void checkCollision (Entity source, Entity target) {
        if (source.testCollide(source)) {
            source.getCollisionEvent(target).checkEvent();
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

package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.MapLevel;
import fr.r1r0r0.deltaengine.model.elements.CrossableVisitor;
import fr.r1r0r0.deltaengine.model.elements.Entity;

/**
 * A physical engine
 * TODO: les marge d erreur deplacement pour les recentrer
 * TODO: prendre en compte les dimensions de l entite pour le deplacement
 * TODO: le points est topleft et prendre en compte les dimensions
 * TODO: On synchronise le run en cas de modif de map
 */
final class PhysicsEngine implements Engine {

    /**
     * Explication de la mise en place des collisions entre une source et une target :
     * la source est une entitite qui posssede une liste de points de collision
     * qui par defaut correspondent aux points :
     * topleft, topright, botleft, botright, center, centerleft, centerright, centertop, centerbot
     * puis on va tester l appartenance d un de ces points dans la hitbox de la cible
     * qui correspond a un minX maxX minY maxY
     */

    private MapLevel mapLevel;
    private long previousRunTime;
    private long maxRunDelta;

    /**
     * Constructor
     */
    public PhysicsEngine () {
        mapLevel = null;
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
     * Update the coordinate of all entity in the current mapLevel
     */
    @Override
    public void run() {
        synchronized (this) {
            long currentRunTime = System.currentTimeMillis();
            long deltaTime = currentRunTime - previousRunTime;
            double timeRatio = (double) Math.min(deltaTime, maxRunDelta) / 1000;
            previousRunTime = currentRunTime;
            if (mapLevel != null) {
                for (Entity entity : mapLevel.getEntities()) {
                    updateCoordinate(entity,timeRatio);
                }
                for (Entity source : mapLevel.getEntities()) {
                    for (Entity target : mapLevel.getEntities()) {
                        checkCollision(source,target);
                    }
                }
            }
        }
    }

    /**
     * TODO
     * @param source
     * @param target
     */
    private void checkCollision (Entity source, Entity target) {
        if (source.testCollide(target)) {
            source.getCollisionEvent(target).checkEvent();
        }
    }

    /**
     * Update the coordinate of an entity
     * @param entity an entity
     * @param timeRatio a ratio of time used to calc the movement
     */
    private void updateCoordinate (Entity entity, double timeRatio) {
        Coordinates nextCoordinate = entity.getCoordinates().getNextCoordinates(entity.getDirection(),
                entity.getSpeed()*timeRatio);
        if (isValidCoordinates(nextCoordinate)) entity.setCoordinates(nextCoordinate);
        else entity.setDirection(Direction.IDLE);
    }

    /**
     * Returns if the coordinate is a valid coordinate in the mapLevel
     * @param coordinates a coordinate
     * @return if the coordinate is a valid coordinate in the mapLevel
     */
    private boolean isValidCoordinates (Coordinates coordinates) {
        int x = (coordinates.getX() >= 0) ? (int) coordinates.getX() : ((int) coordinates.getX() - 1);
        int y = (coordinates.getY() >= 0) ? (int) coordinates.getY() : ((int) coordinates.getY() - 1);
        return CrossableVisitor.isCaseCrossable(mapLevel.getCase(x,y));
    }

    /**
     * Replace the current mapLevel with another
     * @param mapLevel a mapLevel
     */
    public synchronized void setMap(MapLevel mapLevel) {
        this.mapLevel = mapLevel;
    }

    /**
     * Clear the current mapLevel
     */
    public synchronized void clearMap() {
        mapLevel = null;
    }

    /**
     * TODO
     * @param fps
     */
    public void setMaxRunDelta(int fps) {
        maxRunDelta = 1000 / fps;
    }
}

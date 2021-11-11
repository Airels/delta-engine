package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.MapLevel;
import fr.r1r0r0.deltaengine.model.elements.CrossableVisitor;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A physical engine
 * TODO: les marge d erreur deplacement pour les recentrer
 * TODO: prendre en compte les dimensions de l entite pour le deplacement
 * TODO: le points est topleft et prendre en compte les dimensions
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

    private static final double MOVE_MARGIN_ERROR = 0.05;

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
    public synchronized void run() {
        long currentRunTime = System.currentTimeMillis();
        double timeRatio = (double) Math.min(currentRunTime - previousRunTime, maxRunDelta) / 1000;
        previousRunTime = currentRunTime;
        if (mapLevel != null) {
            Collection<Entity> entities = mapLevel.getEntities();
            updateCoordinates(entities,timeRatio);
            checkCollisions(entities).forEach(Event::checkEvent);
        }
    }

    /**
     Return a list of event providing from collisions between all entities in the collection given
     * If there is a collision between 2 entity A and B, the list of event that will be return will contain
     * the event from A to B and the event from B to A.
     * @param entities a collection of entity
     * @return a set of event (without duplicate element)
     */
    private Set<Event> checkCollisions (Collection<Entity> entities) {
        Set<Event> collisionEvents = new HashSet<>(entities.size());
        for (Entity source : entities) {
            for (Entity target : entities) {
                if (source.testCollide(target)) {
                    collisionEvents.add(source.getCollisionEvent(target));
                    collisionEvents.add(target.getCollisionEvent(source));
                }
            }
        }
        return collisionEvents;
    }

    /**
     * Update the coordinates of each entity in an collection of entities
     * @param entities a collection of entity
     * @param timeRatio a ratio of time used to calc the movement
     */
    private void updateCoordinates (Collection<Entity> entities, double timeRatio) {
        for (Entity entity : entities) {
            Coordinates nextCoordinate = entity.getCoordinates().getNextCoordinates(entity.getDirection(),
                    entity.getSpeed() * timeRatio);
            if (isValidCoordinates(nextCoordinate)) entity.setCoordinates(nextCoordinate);
            else entity.setDirection(Direction.IDLE);
        }
    }

    /**
     * Returns if the coordinate is a valid coordinate in the mapLevel
     * @param coordinates a coordinate
     * @return if the coordinate is a valid coordinate in the mapLevel
     */
    private boolean isValidCoordinates (Coordinates coordinates) {
        int x = (coordinates.getX() >= 0) ? (int) coordinates.getX() : ((int) coordinates.getX() - 1);
        int y = (coordinates.getY() >= 0) ? (int) coordinates.getY() : ((int) coordinates.getY() - 1);
        return CrossableVisitor.isCaseCrossable(mapLevel.getCell(x,y));
    }

    /**
     * Replace the current mapLevel with another
     * @param mapLevel a mapLevel
     */
    public synchronized void setMap (MapLevel mapLevel) {
        this.mapLevel = mapLevel;
    }

    /**
     * Clear the current mapLevel
     */
    public synchronized void clearMap () {
        mapLevel = null;
    }

    /**
     * Setter for the attribute maxRunDelta
     * The new value of the attribute is calc by using the number of fps
     * @param fps the number of fps
     */
    public synchronized void setMaxRunDelta (int fps) {
        maxRunDelta = 1000 / fps;
    }

}

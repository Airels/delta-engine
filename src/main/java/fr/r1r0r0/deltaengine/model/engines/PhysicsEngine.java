package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.elements.CrossableVisitor;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A physical engine
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

    private static final double MOVE_MARGIN_ERROR = 0.03;
    private static final CollisionPositions[] POSITIONS_CHECK = new CollisionPositions[]{
            CollisionPositions.LEFT_TOP, CollisionPositions.RIGHT_TOP,
            CollisionPositions.LEFT_BOT, CollisionPositions.RIGHT_BOT};

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
     * Update the coordinates of each entity in a collection of entity
     * If the coordinates can not be update, because of an illegal movement, the direction of the entity is set to IDLE
     * @param entities a collection of entity
     * @param timeRatio a ratio of time used to calc the movement
     */
    private void updateCoordinates (Collection<Entity> entities, double timeRatio) {
        for (Entity entity : entities) {
            if (entity.getDirection() == Direction.IDLE) continue;
            Coordinates<Double> nextCoordinate = entity.getCoordinates().getNextCoordinates(entity.getDirection(),
                    entity.getSpeed() * timeRatio);
            if (isValidPosition(nextCoordinate,entity.getDimension())) entity.setCoordinates(nextCoordinate);
            else entity.setDirection(Direction.IDLE);
        }
    }

    /**
     * Returns if the rectangle given, construct with a top-left point and a dimension,
     * is in a valid position in the mapLevel
     * @param initialTopLeft the coordinate of the top-left point of the rectangle
     * @param dimension the dimension of the rectangle
     * @return if the position of the rectangle is valid in the mapLevel
     */
    private boolean isValidPosition (Coordinates<Double> initialTopLeft, Dimension dimension) {
        for (CollisionPositions collisionPosition : POSITIONS_CHECK) {
            Coordinates<Double> position = collisionPosition.calcPosition(initialTopLeft,dimension,MOVE_MARGIN_ERROR);
            int x = (position.getX() >= 0) ? position.getX().intValue() : (position.getX().intValue() - 1);
            int y = (position.getY() >= 0) ? position.getY().intValue() : (position.getY().intValue() - 1);
            if ( ! CrossableVisitor.isCaseCrossable(mapLevel.getCell(x,y))) return false;
        }
        return true;
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

package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
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

    private static final CollisionPositions[] POSITIONS_CHECK = new CollisionPositions[]{
            CollisionPositions.LEFT_TOP, CollisionPositions.RIGHT_TOP,
            CollisionPositions.LEFT_BOT, CollisionPositions.RIGHT_BOT};

    private MapLevel mapLevel;
    private long previousRunTime;
    private long maxRunDelta;
    private double maxRunDeltaRatio;
    private double marginError;

    /**
     * Constructor
     */
    public PhysicsEngine () {
        mapLevel = null;
        previousRunTime = System.currentTimeMillis();
        maxRunDelta = 0;
        maxRunDeltaRatio = 0;
        marginError = 0;
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
            Coordinates<Double> nextCoordinate = calcNextPosition(entity,timeRatio);
            if (isValidNextPosition(entity,nextCoordinate)) entity.setCoordinates(nextCoordinate);
            else entity.setDirection(Direction.IDLE);
        }
    }

    /**
     * Return the next position of the entity
     * @param entity an entity
     * @param deltaTime a delta of time
     * @return the next position of the entity
     */
    private Coordinates<Double> calcNextPosition (Entity entity, double deltaTime) {
        return calcNextPosition(entity.getCoordinates(),entity.getDirection(),entity.getSpeed(),deltaTime);
    }

    /**
     * Return the next position of the object describe by a topLeft, a direction, a speed, and
     * a delta of time
     * @param topLeft a position topLeft of the object
     * @param direction the direction of the object
     * @param speed the speed of the object
     * @param deltaTime a delta of time
     * @return the next position of the object describe
     */
    private Coordinates<Double> calcNextPosition (Coordinates<Double> topLeft, Direction direction,
                                                  double speed, double deltaTime) {
        return topLeft.getNextCoordinates(direction,speed * deltaTime);
    }

    /**
     * Return if the entity can move with the direction given in argument
     * @param entity an entity
     * @param direction a direction
     * @return if the entity can move with the direction given
     */
    public boolean isAvailableDirection (Entity entity, Direction direction) {
        Coordinates<Double> nextPosition =
                calcNextPosition(entity.getCoordinates(),direction,entity.getSpeed(),maxRunDeltaRatio);
        //System.out.println(entity.getCoordinates() + " " + direction + " " + entity.getSpeed() + " " + maxRunDelta + " -> " + nextPosition);
        return isValidNextPosition(entity,nextPosition);
    }

    /**
     * TODO
     * @param entity
     * @param direction
     * @return
     */
    public boolean canGoToNextCell (Entity entity, Direction direction) {
        if ( ! fitOnCell(entity)) return false;
        Coordinates<Double> position = entity.getCoordinates();
        int x = position.getX().intValue();
        int y = position.getY().intValue();
        Coordinates<Integer> coordinates = direction.getCoordinates();
        return mapLevel.getCell(x + coordinates.getX(),y + coordinates.getY()).isCrossableBy(entity);
    }

    /**
     * TODO
     * @param entity
     * @return
     */
    private boolean fitOnCell (Entity entity) {
        Coordinates<Double> position = entity.getCoordinates();
        Dimension dimension = entity.getDimension();
        Coordinates<Double> leftTop = CollisionPositions.LEFT_TOP.calcPosition(position,dimension,marginError);
        Coordinates<Double> rightBot = CollisionPositions.RIGHT_BOT.calcPosition(position,dimension,marginError);
        return leftTop.getX().intValue() == rightBot.getX().intValue()
                && leftTop.getY().intValue() == rightBot.getY().intValue();
    }

    /**
     * Returns if the next position of the entity is in a valid position in the mapLevel
     * @param entity an entity
     * @param nextPosition the next position of the entity
     * @return if the position of the rectangle is valid in the mapLevel
     */
    private boolean isValidNextPosition (Entity entity, Coordinates<Double> nextPosition) {
        for (CollisionPositions collisionPosition : POSITIONS_CHECK) {
            Coordinates<Double> position =
                    collisionPosition.calcPosition(nextPosition,entity.getDimension(),marginError);
            int x = (position.getX() >= 0) ? position.getX().intValue()
                    : (position.getX().intValue() - 1);
            int y = (position.getY() >= 0) ? position.getY().intValue()
                    : (position.getY().intValue() - 1);
            if ( ! mapLevel.getCell(x,y).isCrossableBy(entity)) return false;
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
        maxRunDeltaRatio = (double) maxRunDelta / 1000;
    }

    /**
     * Setter for the attribute marginError
     * @param marginError the margin error
     */
    public synchronized void setMarginError (double marginError) {
        this.marginError = marginError;
    }

}

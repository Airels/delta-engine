package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.exceptions.AIAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.VoidEvent;
import fr.r1r0r0.deltaengine.model.AI;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.*;

/**
 * An entity of the engine. An entity is detached from cases, and can freely move on them.
 * Every object of the game could be an entity.
 * An AI could also be attached to an Entity.
 */
public class Entity implements Element {

    private Sprite sprite;
    private final String name;
    private Coordinates coords;
    private Direction direction;
    private double speed;
    private AI attachedAI;
    private Dimension dimension;
    private final Map<Entity, Event> collisionEvents;

    /**
     * Default constructor. Defines all attributes to an entity.
     * @param name name of the entity
     * @param coords coordinates of the entity
     * @param sprite sprite of the entity
     */
    public Entity(String name, Coordinates coords, Sprite sprite, Dimension dimension) {
        this.name = name;
        this.coords = coords;
        this.sprite = sprite;
        direction = Direction.IDLE;
        speed = 0;
        attachedAI = null;
        this.dimension = dimension;
        this.collisionEvents = new HashMap<>();
        sprite.setZOrder(100);
    }

    /**
     * Current direction of the entity
     * @return direction of the entity
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Set current direction of the entity
     * @param direction current direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Current speed of the entity. Speed is in case/frame_rate. <br>
     * If current frame_rate is 60, then an entity with 1 of speed will move one case to another in 60 frames.
     * @return double speed entity
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Set new current speed of the entity. Speed is in case/frame_rate. <br>
     * If current frame_rate is 60, then an entity with 1 of speed will move one case to another in 60 frames.
     * @param speed speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * AI to attach to the entity. Can be called once. Once AI is attached, it is impossible to remove or change it.
     * @param ia AI to attach to the entity
     * @throws AIAlreadyAttachedException if an AI is already attached to the entity
     */
    public final void setAI(AI ia) throws AIAlreadyAttachedException {
        if (attachedAI != null)
            throw new AIAlreadyAttachedException(this);

        ia.setEntity(this);
        this.attachedAI = ia;
    }

    /**
     * Get attached AI of the entity
     * @return attached AI
     */
    public final AI getAI() {
        return attachedAI;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    @Override
    public void setDimension (Dimension dimension) {
        this.dimension = dimension;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public Coordinates getCoordinates() {
        return coords;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coords = coordinates;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Set a new collision event bound to given entity.
     * @param entity entity to react with
     * @param event event to play when a collision is detected
     */
    public void setCollisionEvent(Entity entity, Event event) {
        collisionEvents.put(entity, event);
    }

    /**
     * Clear all collision events bound to given entity
     * @param entity bound entity to clear events
     */
    public void clearCollisionEvent(Entity entity) {
        collisionEvents.remove(entity);
    }

    /**
     * Clear all collision events.
     */
    public void clearAllCollisionEvents() {
        collisionEvents.clear();
    }

    /**
     * Return the event matching to the entity given
     * @param entity an entity
     * @return the event matching to the entity given, the VodiEvent is return if there is no matching event
     */
    public Event getCollisionEvent (Entity entity) {
        Event event = collisionEvents.get(entity);
        return (event == null) ? VoidEvent.getInstance() : event;
    }

    /**
     * Return the collision points, a list of coordinates used to calc if there is a collision between 2 entities.
     * The hit-box is a rectangle, with a left-top point corresponding to attribute coords,
     * and width/height are determined by the attribute dimension.
     * Collisions points are calc using class CollisionPoints
     * @return a list of coordinates representing collision points
     */
    private Collection<Coordinates> getCollisionPoints () {
        Collection<Coordinates> collisionPoints = new ArrayList<>(CollisionPositions.values().length);
        for (CollisionPositions collisionPosition : CollisionPositions.values()) {
            collisionPoints.add(collisionPosition.calcPosition(coords,dimension));
        }
        return collisionPoints;
    }

    /**
     * Return is there is the current entity is colliding the other entity given.
     * There is a collision if one of the collision points of the current entity is contained in the
     * hit-box of the other entity.
     * @param other an entity
     * @return if there is a collision
     */
    public final boolean testCollide (Entity other) {
        for (Coordinates collisionPoint : getCollisionPoints()) {
            if (CollisionPositions.isInHitBox(other.coords,other.dimension,collisionPoint)) return true;
        }
        return false;
    }

}

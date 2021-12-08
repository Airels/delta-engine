package fr.r1r0r0.deltaengine.model.elements.entity;

import fr.r1r0r0.deltaengine.exceptions.AIAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.elements.Element;
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
public class Entity implements Element<Double> {

    private EntityAttributes entityAttributes;

    /**
     * Default constructor. Defines all attributes to an entity.
     * @param name name of the entity
     * @param coords coordinates of the entity
     * @param sprite sprite of the entity
     * @param dimension a dimension
     * @param hitBox a dimension
     */
    public Entity(String name, Coordinates<Double> coords, Sprite sprite, Dimension dimension, Dimension hitBox) {
        sprite.setZOrder(100);
        entityAttributes = new EntityAttributes(name, coords, sprite, dimension, hitBox, Direction.IDLE, 0);
    }

    /**
     * Constructor
     * @param name a name
     * @param coords a coordinates
     * @param sprite a sprite
     * @param dimension a dimension
     */
    public Entity(String name, Coordinates<Double> coords, Sprite sprite, Dimension dimension) {
        this(name,coords,sprite,dimension,dimension.copy());
    }

    /**
     * Getter fot the attribute hitBox
     * @return the attribute hitBox
     */
    public Dimension getHitBox() {return entityAttributes.getHitBox();}

    /**
     * Setter for the attribute hitBox
     * @param hitBox a dimension for the hitBox
     */
    public void setHitBox(Dimension hitBox) {entityAttributes.setHitBox(hitBox);}

    /**
     * Current direction of the entity
     * @return direction of the entity
     */
    public Direction getDirection() {
        return entityAttributes.getDirection();
    }

    /**
     * Set current direction of the entity
     * @param direction current direction
     */
    public void setDirection(Direction direction) {
        entityAttributes.setDirection(direction);
    }

    /**
     * Current speed of the entity. Speed is in case/frame_rate. <br>
     * If current frame_rate is 60, then an entity with 1 of speed will move one case to another in 60 frames.
     * @return double speed entity
     */
    public double getSpeed() {
        return entityAttributes.getSpeed();
    }

    /**
     * Set new current speed of the entity. Speed is in case/frame_rate. <br>
     * If current frame_rate is 60, then an entity with 1 of speed will move one case to another in 60 frames.
     * @param speed speed to set
     */
    public void setSpeed(double speed) {
        entityAttributes.setSpeed(speed);
    }

    /**
     * AI to attach to the entity. Can be called once. Once AI is attached, it is impossible to remove or change it.
     * @param ai AI to attach to the entity
     * @throws AIAlreadyAttachedException if an AI is already attached to the entity
     */
    public void setAI(AI ai) throws AIAlreadyAttachedException {
        entityAttributes.setAttachedAI(this, ai);
    }

    /**
     * Get attached AI of the entity
     * @return attached AI
     */
    public AI getAI() {
        return entityAttributes.getAttachedAI();
    }

    @Override
    public Dimension getDimension() {
        return entityAttributes.getDimension();
    }

    @Override
    public void setDimension (Dimension dimension) {
        entityAttributes.setDimension(dimension);
    }

    @Override
    public Sprite getSprite() {
        return entityAttributes.getSprite();
    }

    @Override
    public void setSprite(Sprite sprite) {
        entityAttributes.setSprite(sprite);
    }

    @Override
    public Coordinates<Double> getCoordinates() {
        return entityAttributes.getCoordinates();
    }

    @Override
    public void setCoordinates(Coordinates<Double> coordinates) {
        entityAttributes.setCoordinates(coordinates);
    }

    @Override
    public String getName() {
        return entityAttributes.getName();
    }

    /**
     * Set a new collision event bound to given entity.
     * @param entity entity to react with
     * @param event event to play when a collision is detected
     */
    public void setCollisionEvent(Entity entity, Event event) {
        entityAttributes.getCollisionsEvents().put(entity, event);
    }

    /**
     * Clear all collision events bound to given entity
     * @param entity bound entity to clear events
     */
    public void clearCollisionEvent(Entity entity) {
        entityAttributes.getCollisionsEvents().remove(entity);
    }

    /**
     * Clear all collision events.
     */
    public void clearAllCollisionEvents() {
        entityAttributes.getCollisionsEvents().clear();
    }

    /**
     * Return the event matching to the entity given
     * @param entity an entity
     * @return the event matching to the entity given, the VoidEvent is return if there is no matching event
     */
    public Event getCollisionEvent (Entity entity) {
        Event event = entityAttributes.getCollisionsEvents().get(entity);
        return (event == null) ? VoidEvent.getInstance() : event;
    }

    /**
     * Return the collision points, a list of coordinates used to calc if there is a collision between 2 entities.
     * The hit-box is a rectangle, with a left-top point corresponding to attribute coords,
     * and width/height are determined by the attribute dimension.
     * Collisions points are calc using class CollisionPoints
     * @return a list of coordinates representing collision points
     */
    private Collection<Coordinates<Double>> getCollisionPoints () {
        Collection<Coordinates<Double>> collisionPoints = new ArrayList<>(CollisionPositions.values().length);
        Coordinates<Double> coordinates = CollisionPositions.calcTopLetHitBox(this);
        Dimension hitBox = getHitBox();
        for (CollisionPositions collisionPosition : CollisionPositions.BASIC_COLLISION_POSITION) {
            collisionPoints.add(collisionPosition.calcPosition(coordinates,hitBox));
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
    public boolean testCollide (Entity other) {
        Coordinates<Double> otherCoordinates = CollisionPositions.calcTopLetHitBox(other);
        Dimension otherHitBox = other.getHitBox();
        for (Coordinates<Double> collisionPoint : getCollisionPoints()) {
            if (CollisionPositions.isInHitBox(otherCoordinates,otherHitBox,collisionPoint)) return true;
        }
        return false;
    }

    /**
     * Get all attributes packaged in an object, allowing to getting, setting them or attach listeners when they are updated
     * @return EntityAttribute all entity attributes
     */
    public EntityAttributes getAttributes() {
        return entityAttributes;
    }

    private Coordinates<Integer> blockTarget;
    public void setBlockTarget (Coordinates<Integer> blockTarget) {
        this.blockTarget = blockTarget;
    }
    public Coordinates<Integer> getBlockTarget () {
        return blockTarget;
    }
    public void resetBlockTarget () {
        blockTarget = null;
    }

}

package fr.r1r0r0.deltaengine.model.elements.entity;

import fr.r1r0r0.deltaengine.exceptions.AIAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.AI;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.events.AttributeListener;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * All attributes of an Entity.
 * Used to get and store all Entity attributes, and define actions when these attributes are updated
 */
public final class EntityAttributes {

    private final String name;
    private Coordinates<Double> coordinates;
    private Sprite sprite;
    private Dimension dimension;
    private Direction direction;
    private double speed;
    private AI attachedAI;
    private final Map<Entity, Event> collisionsEvents;

    private final Collection<AttributeListener<Coordinates<Double>>> coordinatesListeners;
    private final Collection<AttributeListener<Sprite>> spriteListeners;
    private final Collection<AttributeListener<Dimension>> dimensionListeners;
    private final Collection<AttributeListener<Direction>> directionListeners;
    private final Collection<AttributeListener<Double>> speedListeners;


    /**
     * Default constructor. Inits all attributes of an Entity
     *
     * @param name        name of entity
     * @param coordinates initial coordinates of entity
     * @param sprite      initial sprite of entity
     * @param dimension   dimension of entity
     * @param direction   initial direction of entity
     * @param speed       initial speed of entity
     */
    EntityAttributes(String name, Coordinates<Double> coordinates, Sprite sprite, Dimension dimension, Direction direction, double speed) {
        this.name = name;
        this.coordinates = coordinates;
        this.sprite = sprite;
        this.dimension = dimension;
        this.direction = direction;
        this.speed = speed;
        this.collisionsEvents = new HashMap<>();

        coordinatesListeners = new LinkedList<>();
        spriteListeners = new LinkedList<>();
        dimensionListeners = new LinkedList<>();
        directionListeners = new LinkedList<>();
        speedListeners = new LinkedList<>();
    }

    /**
     * Returns name of the entity
     *
     * @return String name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Get current position of the entity
     *
     * @return Coordinates of the entity
     */
    public Coordinates<Double> getCoordinates() {
        return coordinates;
    }

    /**
     * Set new position of the entity and triggers all associated triggers
     *
     * @param coordinates Coordinates to set
     */
    public void setCoordinates(Coordinates<Double> coordinates) {
        Coordinates<Double> oldCoordinates = this.coordinates;
        this.coordinates = coordinates;

        coordinatesListeners.forEach(e -> e.attributeUpdated(oldCoordinates, coordinates));
    }

    /**
     * Get current sprite of the entity
     *
     * @return Sprite of the entity
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Set new sprite for the entity and triggers all associated triggers
     *
     * @param sprite Sprite new sprite to apply
     */
    public void setSprite(Sprite sprite) {
        Sprite oldSprite = this.sprite;
        this.sprite = sprite;

        spriteListeners.forEach(e -> e.attributeUpdated(oldSprite, sprite));
    }

    /**
     * Get current dimension of the entity
     *
     * @return Dimension of the entity
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Set new dimension for the entity and triggers all associated triggers
     *
     * @param dimension new Dimension of the entity
     */
    public void setDimension(Dimension dimension) {
        Dimension oldDimension = this.dimension;
        this.dimension = dimension;

        dimensionListeners.forEach(e -> e.attributeUpdated(oldDimension, dimension));
    }

    /**
     * Get current direction of the entity
     *
     * @return Direction current direction of the entity
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Set new direction for the entity and triggers all associated triggers
     *
     * @param direction new Direction to apply
     */
    public void setDirection(Direction direction) {
        Direction oldDirection = this.direction;
        this.direction = direction;

        directionListeners.forEach(e -> e.attributeUpdated(oldDirection, direction));
    }

    /**
     * Get current speed of the entity
     *
     * @return double current speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Set new speed for the entity and triggers all associated triggers
     *
     * @param speed double new speed to apply
     */
    public void setSpeed(double speed) {
        double oldSpeed = this.speed;
        this.speed = speed;

        speedListeners.forEach(e -> e.attributeUpdated(oldSpeed, speed));
    }

    /**
     * Get current AI of the entity
     *
     * @return AI of the entity
     */
    public AI getAttachedAI() {
        return attachedAI;
    }

    /**
     * AI to attach to the entity. Can be called once. Once AI is attached, it is impossible to remove or change it.
     *
     * @param entity Entity concerned
     * @param ai     AI to bind
     * @throws AIAlreadyAttachedException if an AI is already attached to this entity
     */
    public void setAttachedAI(Entity entity, AI ai) throws AIAlreadyAttachedException {
        if (attachedAI != null)
            throw new AIAlreadyAttachedException(entity);

        ai.setEntity(entity);
        attachedAI = ai;
    }

    /**
     * Get map of collisions event, to produce actions when entities collide each others
     *
     * @return Map of all registered collision events
     */
    public Map<Entity, Event> getCollisionsEvents() {
        return collisionsEvents;
    }

    /**
     * Set a new listener to the Coordinates attribute. Listener will be executed when Coordinates are updated.
     * @param e listener to add
     */
    public void addCoordinatesListener(AttributeListener<Coordinates<Double>> e) {
        coordinatesListeners.add(e);
    }

    /**
     * Set a new listener to the Sprite attribute. Listener will be executed when Sprite is updated.
     * @param e listener to add
     */
    public void addSpriteListener(AttributeListener<Sprite> e) {
        spriteListeners.add(e);
    }

    /**
     * Set a new listener to the Dimension attribute. Listener will be executed when Dimension is updated.
     * @param e listener to add
     */
    public void addDimensionListener(AttributeListener<Dimension> e) {
        dimensionListeners.add(e);
    }

    /**
     * Set a new listener to the Direction attribute. Listener will be executed when Direction is updated.
     * @param e listener to add
     */
    public void addDirectionListener(AttributeListener<Direction> e) {
        directionListeners.add(e);
    }

    /**
     * Set a new listener to the Speed attribute. Listener will be executed when Speed is updated.
     * @param e listener to add
     */
    public void addSpeedListener(AttributeListener<Double> e) {
        speedListeners.add(e);
    }
}

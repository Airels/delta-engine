package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.exceptions.IAAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.IA;
import fr.r1r0r0.deltaengine.model.elements.Element;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.VoidEvent;
import fr.r1r0r0.deltaengine.model.AI;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.geometry.Dimension2D;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private AI attachedIA;
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
        attachedIA = null;
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
     * @throws IAAlreadyAttachedException if an AI is already attached to the entity
     */
    public final void setIA(AI ia) throws IAAlreadyAttachedException {
        if (attachedIA != null)
            throw new IAAlreadyAttachedException(this);

        ia.setEntity(this);
        this.attachedIA = ia;
    }

    /**
     * Get attached AI of the entity
     * @return attached AI
     */
    public final AI getIA() {
        return attachedIA;
    }

    @Override
    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension (Dimension dimension) {
        this.dimension = dimension;
    }

    /**
     * Returns sprite of the entity
     *
     * @return Sprite of the entity
     */
    @Override
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Set sprite of an entity
     *
     * @param sprite sprite to set
     */
    @Override
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Coordinates of the entity in the game.
     *
     * @return Coordinates of the entity
     */
    @Override
    public Coordinates getCoordinates() {
        return coords;
    }

    /**
     * Allows setting new coordinates for the entity.
     *
     * @param coordinates new coordinates
     */
    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.coords = coordinates;
    }

    /**
     * Name getter of the entity
     *
     * @return String name of the entity
     */
    @Override
    public String getName() {
        return name;
    }

    public void setCollisionEvent(Entity entity, Event event) {
        collisionEvents.put(entity, event);
    }

    public void clearCollisionEvent(Entity entity) {
        collisionEvents.remove(entity);
    }

    public void clearAllCollisionEvents() {
        collisionEvents.clear();
    }

    public Event getCollisionEvent(Entity entity) {
        Event event = collisionEvents.get(entity);
        return (event == null) ? new VoidEvent() : event;
    }

    private Collection<Coordinates> getCollisionPoints () {
        //TODO: voir pour mettre ses points par defaut dans la classe
        double centerX = coords.getX() + (dimension.getWidth() / 2);
        double centerY = coords.getY() + (dimension.getHeight() / 2);
        double maxX = coords.getX() + dimension.getWidth();
        double maxY = coords.getX() + dimension.getHeight();
        return List.of(new Coordinates(coords.getX(),coords.getY()), // TOP LEFT
                new Coordinates(maxX,coords.getY()), // TOP RIGHT
                new Coordinates(coords.getX(),maxY), // BOT LEFT
                new Coordinates(maxX,maxY), // BOT RIGHT
                new Coordinates(centerX,centerY), // CENTER
                new Coordinates(coords.getX(),centerY), // CENTER LEFT
                new Coordinates(maxX,centerY), // CENTER RIGHT
                new Coordinates(centerX,coords.getY()), // CENTER TOP
                new Coordinates(centerX,maxY) // CENTER BOT
        );
    }

    public boolean testCollide (Entity other) {
        Coordinates otherCoords = other.coords;
        double minX = otherCoords.getX();
        double minY = otherCoords.getY();
        double maxX = otherCoords.getX() + other.dimension.getWidth();
        double maxY = otherCoords.getY() + other.dimension.getHeight();
        for (Coordinates collisionPoint : getCollisionPoints()) {
            if (minX <= collisionPoint.getX() && collisionPoint.getX() <= maxX
                    && minY <= collisionPoint.getY() && collisionPoint.getY() <= maxY)
                return true;
        }
        return false;
    }

}

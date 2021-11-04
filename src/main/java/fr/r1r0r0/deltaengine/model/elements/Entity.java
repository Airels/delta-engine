package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.exceptions.IAAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.AI;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

/**
 * An entity of the engine. An entity is detached from cases, and can freely move on them.
 * Every object of the game could be an entity.
 * An AI could also be attached to an Entity.
 */
public class Entity implements Element {

    private Sprite sprite;
    private String name;
    private Coordinates coords;
    private Direction direction;
    private double speed;
    private AI attachedIA;

    /**
     * Default constructor. Defines all attributes to an entity.
     * @param name name of the entity
     * @param coords coordinates of the entity
     * @param sprite sprite of the entity
     */
    public Entity(String name, Coordinates coords, Sprite sprite) {
        this.name = name;
        this.coords = coords;
        this.sprite = sprite;
        direction = Direction.IDLE;
        speed = 0;
        attachedIA = null;
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
}

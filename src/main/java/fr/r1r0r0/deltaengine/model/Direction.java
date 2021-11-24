package fr.r1r0r0.deltaengine.model;

/**
 * Current directions. IDLE means no movement.
 */
public enum Direction {

    LEFT(new Coordinates<>(-1,0)),
    UP(new Coordinates<>(0,-1)),
    RIGHT(new Coordinates<>(1,0)),
    DOWN(new Coordinates<>(0,1)),
    IDLE(new Coordinates<>(0,0));

    private final Coordinates<Integer> coordinates;

    /**
     * Constructor
     * @param coordinates a coordinate
     */
    Direction (Coordinates<Integer> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Getter of attribute coordinates
     * @return attribute coordinates
     */
    public Coordinates<Integer> getCoordinates () {
        return coordinates;
    }

    /**
     * Returns the opposite of the direction
     * @return Direction
     */
    public Direction getOpposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> IDLE;
        };
    }

    /**
     * Returns X value of the Direction
     * @return Integer
     */
    public int getX() {
        return coordinates.getX();
    }

    /**
     * Returns Y value of the Direction
     * @return Integer
     */
    public int getY() {
        return coordinates.getY();
    }
}

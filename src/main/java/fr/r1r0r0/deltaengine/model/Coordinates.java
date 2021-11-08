package fr.r1r0r0.deltaengine.model;

/**
 * Coordinates of a point, or object, coded by two doubles for 2D position.
 */
public class Coordinates {
    private double x, y;

    /**
     * Default constructor. Creates a Coordinates given x and y positions.
     * @param x horizontal position
     * @param y vertical position
     */
    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get saved x position.
     * @return x double current x of an element
     */
    public double getX() {
        return x;
    }

    /**
     * Get saved y position.
     * @return y double current y of an element
     */
    public double getY() {
        return y;
    }

    /**
     * Calc and returns the next coordinates matching to the current coordinate,
     * a direction, and a delta of movement
     * @param direction the direction where the movement will be applied
     * @param delta the delta of movement
     * @return the next coordinate
     */
    public Coordinates getNextCoordinates (Direction direction, double delta) {
        Coordinates other = direction.getCoordinates();
        return new Coordinates(x + other.x * delta,y + other.y * delta);
    }

}

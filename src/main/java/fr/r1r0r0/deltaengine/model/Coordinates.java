package fr.r1r0r0.deltaengine.model;

import java.util.Objects;

/**
 * Coordinates of a point, or object, coded by two doubles for 2D position.
 * @param <T> the type of Number used, example : Integer, Double..
 */
public final class Coordinates <T extends Number> {

    private final T x;
    private final T y;

    /**
     * Default constructor. Creates a Coordinates given x and y positions.
     * @param x horizontal position
     * @param y vertical position
     */
    public Coordinates (T x, T y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get saved x position.
     * @return x double current x of an element
     */
    public T getX () {
        return x;
    }

    /**
     * Get saved y position.
     * @return y double current y of an element
     */
    public T getY () {
        return y;
    }

    /**
     * Calc and returns the next coordinates matching to the current coordinate,
     * a direction, and a delta of movement
     * @param direction the direction where the movement will be applied
     * @param multiplier the multiplier apply to the movement
     * @return the next coordinate
     */
    public Coordinates<Double> getNextCoordinates (Direction direction, double multiplier) {
        return new Coordinates<>(x.doubleValue() + direction.getX() * multiplier,
                y.doubleValue() + direction.getY() * multiplier);
    }

    public static Coordinates<Integer> doubleToInteger (Coordinates<Double> coordinates) {
        int xInt = (coordinates.getX() >= 0) ? coordinates.getX().intValue()
                : (coordinates.getX().intValue() - 1);
        int yInt = (coordinates.getY() >= 0) ? coordinates.getY().intValue()
                : (coordinates.getY().intValue() - 1);
        return new Coordinates<>(xInt,yInt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates<T> that = (Coordinates<T>) o;
        return x.equals(that.x) && y.equals(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString () {
        return "x=" + x + ", y=" + y;
    }

    /**
     * Returns a new instance of Coordinates<T> with exact same values
     * @return new Coordinates
     */
    public Coordinates<T> copy() {
        return new Coordinates<>(x, y);
    }
}

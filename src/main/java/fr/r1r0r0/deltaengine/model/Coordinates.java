package fr.r1r0r0.deltaengine.model;

import java.util.Objects;

/**
 * Coordinates of a point, or object, coded by two doubles for 2D position.
 */
public final class Coordinates <E extends Number> {

    private E x, y;

    /**
     * Default constructor. Creates a Coordinates given x and y positions.
     * @param x horizontal position
     * @param y vertical position
     */
    public Coordinates(E x, E y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get saved x position.
     * @return x double current x of an element
     */
    public E getX() {
        return x;
    }

    /**
     * Get saved y position.
     * @return y double current y of an element
     */
    public E getY() {
        return y;
    }

    /**
     * Calc and returns the next coordinates matching to the current coordinate,
     * a direction, and a delta of movement
     * @param direction the direction where the movement will be applied
     * @param delta the delta of movement
     * @return the next coordinate
     */
    public Coordinates<Double> getNextCoordinates (Direction direction, double delta) {
        Coordinates<Integer> other = direction.getCoordinates();
        return new Coordinates<>(x.doubleValue() + other.x * delta,y.doubleValue() + other.y * delta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates<E> that = (Coordinates<E>) o;
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

}

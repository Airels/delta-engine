package fr.r1r0r0.deltaengine.model;

/**
 * Represent the 2D dimension of an elements, a rectangle, used for the graphic size and the hit-box
 */
public final class Dimension {

    private final double width;
    private final double height;

    /**
     * Constructor
     * @param width the width
     * @param height the height
     */
    public Dimension (double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Return the attribute width
     * @return the attribute width
     */
    public double getWidth () {
        return width;
    }

    /**
     * Return the attribute height
     * @return the attribute height
     */
    public double getHeight () {
        return height;
    }
}


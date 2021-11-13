package fr.r1r0r0.deltaengine.model;

/**
 * Represent the 2D dimension of an elements, a rectangle, used for the graphic size and the hit-box
 */
public final class Dimension {

    private final static double MIN_WIDTH = 0;
    private final static double MAX_WIDTH = 1;
    private final static double MIN_HEIGHT = 0;
    private final static double MAX_HEIGHT = 1;

    private final double width;
    private final double height;

    /**
     * Constructor
     * @param width the width
     * @param height the height
     */
    public Dimension (double width, double height) {
        if (width < MIN_WIDTH)
            throw new IllegalArgumentException("Width must be greater than " + MIN_WIDTH + " : " + width);
        if (height < MIN_HEIGHT)
            throw new IllegalArgumentException("Height must be greater than " + MIN_HEIGHT + " : " + height);
        if (width > MAX_WIDTH)
            throw new IllegalArgumentException("Height must be lesser than " + MAX_WIDTH + " : " + width);
        if (height > MAX_HEIGHT)
            throw new IllegalArgumentException("Height must be lesser than " + MAX_HEIGHT + " : " + height);
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


package fr.r1r0r0.deltaengine.model.sprites;

public class Sizes {

    private final double sizeX;
    private final double sizeY;

    /**
     * Constructor
     * @param width the width
     * @param height the height
     */
    public Sizes(double width, double height) {
        this.sizeX = width;
        this.sizeY = height;
    }

    /**
     * Return the attribute scaleX
     * @return the attribute scaleX
     */
    public double getWidth() {
        return sizeX;
    }

    /**
     * Return the attribute scaleY
     * @return the attribute scaleY
     */
    public double getHeight() {
        return sizeY;
    }
}

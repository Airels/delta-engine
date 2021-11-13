package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;

/**
 * A set of point that are used to calculate collision points and hit-box of entity
 */
public enum CollisionPositions {

    LEFT_TOP(0,0,1,1),
    RIGHT_TOP(1,0,-1,1),
    LEFT_BOT(0,1,1,-1),
    RIGHT_BOT(1,1,-1,-1),
    CENTER_CENTER(0.5,0.5,0,0),
    CENTER_TOP(0.5,0,0,1),
    CENTER_BOT(0.5,1,0,-1),
    LEFT_CENTER(0,0.5,1,0),
    RIGHT_CENTER(1,0.5,-1,0);

    private final double ratioDimensionX;
    private final double ratioDimensionY;
    private final double ratioMarginX;
    private final double ratioMarginY;

    /**
     * Constructor
     * @param ratioDimensionX a ratio of dimension X
     * @param ratioDimensionY a ratio of dimension Y
     */
    CollisionPositions (double ratioDimensionX, double ratioDimensionY, double ratioMarginX, double ratioMarginY) {
        this.ratioDimensionX = ratioDimensionX;
        this.ratioDimensionY = ratioDimensionY;
        this.ratioMarginX = ratioMarginX;
        this.ratioMarginY = ratioMarginY;
    }

    /**
     * Return the position calculate in a rectangle
     * @param topLeft the top-left point
     * @param dimension the dimension of the rectangle
     * @return the position calculate in a rectangle
     */
    public Coordinates<Double> calcPosition (Coordinates<Double> topLeft, Dimension dimension) {
        return calcPosition(topLeft,dimension,0);
    }

    /**
     * Return the position calculate in a rectangle with a margin.
     * The margin is used to move calc a point in a rectangle with a lesser size, but with the same center
     * @param topLeft the top-left point
     * @param dimension the dimension of the rectangle
     * @param margin a margin
     * @return the position calculate in a rectangle with a margin.
     */
    public  Coordinates<Double> calcPosition (Coordinates<Double> topLeft, Dimension dimension, double margin) {
        if (margin < 0) throw new IllegalArgumentException("Margin must be greater than 0 : " + margin);
        if (1 < margin) throw new IllegalArgumentException("Margin must be lesser than 1 : " + margin);
        return new Coordinates<>(
                topLeft.getX() + (ratioDimensionX + margin*ratioMarginX)*dimension.getWidth(),
                topLeft.getY() + (ratioDimensionY + margin*ratioMarginY)*dimension.getHeight()
        );
    }

    /**
     * Return if the collisionPoint given is contain in the hit-box described by the topLeft point and the dimension.
     * The hit-box is a rectangle, with a left-top point and width/height are determined by dimension.
     * The points is contain in the rectangle if the condition are satisfied :
     *      -his X is contain between the minX and maxX of the rectangle,
     *          between coords.getX() and coords.getX() + dimension.getWidth()
     *      -his Y is contain between the minY and maxY of the rectangle,
     *          between coords.getY() and coords.getY() + dimension.getHeight()
     * @param topLeft the top-left points of the rectangle
     * @param dimension the dimension of the rectangle
     * @param collisionPoint a point
     * @return if the collisionPoint is include in the rectangle described by the topLeft point and the dimension.
     */
    public static boolean isInHitBox (Coordinates<Double> topLeft, Dimension dimension,
                                      Coordinates<Double> collisionPoint) {
        return topLeft.getX() <= collisionPoint.getX()
                && collisionPoint.getX() <= (topLeft.getX() + dimension.getWidth())
                && topLeft.getY() <= collisionPoint.getY()
                && collisionPoint.getY() <= (topLeft.getY() + dimension.getHeight());
    }

}

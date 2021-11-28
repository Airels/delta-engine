package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;

/**
 * A set of point that are used to calculate collision points and hit-box of entity
 */
public enum CollisionPositions {

    LEFT_TOP(0,0),
    RIGHT_TOP(1,0),
    LEFT_BOT(0,1),
    RIGHT_BOT(1,1),
    CENTER_CENTER(0.5,0.5),
    CENTER_TOP(0.5,0),
    CENTER_BOT(0.5,1),
    LEFT_CENTER(0,0.5),
    RIGHT_CENTER(1,0.5);

    public static final CollisionPositions[] BASIC_COLLISION_POSITION = new CollisionPositions[]{
            LEFT_TOP,RIGHT_TOP,LEFT_BOT,RIGHT_BOT};

    private final double ratioX;
    private final double ratioY;

    /**
     * Constructor
     * @param ratioX a ratio of dimension X
     * @param ratioY a ratio of dimension Y
     */
    CollisionPositions (double ratioX, double ratioY) {
        this.ratioX = ratioX;
        this.ratioY = ratioY;
    }

    /**
     * Return the position calculate in a rectangle
     * @param topLeft the top-left point
     * @param dimension the dimension of the rectangle
     * @return the position calculate in a rectangle
     */
    public Coordinates<Double> calcPosition (Coordinates<Double> topLeft, Dimension dimension) {
        return new Coordinates<>(
                topLeft.getX() + ratioX * dimension.getWidth(),
                topLeft.getY() + ratioY * dimension.getHeight()
        );
    }

    /**
     * TODO
     * @param entity
     * @return
     */
    public static Coordinates<Double> calcTopLetHitBox (Entity entity) {
        return calcTopLetHitBox(entity.getCoordinates(),entity.getDimension(),entity.getHitBox());
    }

    /**
     * TODO
     * @param topLeftDimension
     * @param dimension
     * @param hitBox
     * @return
     */
    private static Coordinates<Double> calcTopLetHitBox (Coordinates<Double> topLeftDimension,
                                                        Dimension dimension, Dimension hitBox) {
        double deltaDimensionX = (dimension.getWidth() - hitBox.getWidth()) / 2;
        double deltaDimensionY = (dimension.getHeight() - hitBox.getHeight()) / 2;
        return new Coordinates<>(topLeftDimension.getX() + RIGHT_BOT.ratioX * deltaDimensionX,
                topLeftDimension.getY() + RIGHT_BOT.ratioY * deltaDimensionY);
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

    /**
     * TODO
     * @param entity1
     * @param entity2
     * @return
     */
    public static boolean simplyCollide (Entity entity1, Entity entity2) {
        Coordinates<Double> topLeft1 = CollisionPositions.calcTopLetHitBox(entity1);
        Coordinates<Double> topLeft2 = CollisionPositions.calcTopLetHitBox(entity2);
        Coordinates<Double> botRight1 = CollisionPositions.RIGHT_BOT.calcPosition(topLeft1,entity1.getHitBox());
        Coordinates<Double> botRight2 = CollisionPositions.RIGHT_BOT.calcPosition(topLeft2,entity2.getHitBox());
        double minX1 = topLeft1.getX();
        double maxX1 = botRight1.getX();
        double minY1 = topLeft1.getY();
        double maxY1 = botRight1.getY();
        double minX2 = topLeft2.getX();
        double maxX2 = botRight2.getX();
        double minY2 = topLeft2.getY();
        double maxY2 = botRight2.getY();
        boolean testX = (minX1 <= minX2 && minX2 <= maxX1)
                || (minX1 <= maxX2 && maxX2 <= maxX1)
                || (minX2 <= minX1 && minX1 <= maxX2)
                || (minX2 <= maxX1 && maxX1 <= maxX2);
        boolean testY = (minY1 <= minY2 && minY2 <= maxY1)
                || (minY1 <= maxY2 && maxY2 <= maxY1)
                || (minY2 <= minY1 && minY1 <= maxY2)
                || (minY2 <= maxY1 && maxY1 <= maxY2);
        return testX && testY;
    }

}

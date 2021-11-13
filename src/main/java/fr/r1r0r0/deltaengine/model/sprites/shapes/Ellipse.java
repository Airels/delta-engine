package fr.r1r0r0.deltaengine.model.sprites.shapes;

import fr.r1r0r0.deltaengine.view.colors.Color;

/**
 * Ellipse Sprite
 */
public class Ellipse extends Shape{

    private final javafx.scene.shape.Ellipse ellipse;

    /**
     * Instantiate Ellipse Sprite
     * @param radiusX radius in the X direction
     * @param radiusY radius in the Y direction
     * @param color of the ellipse
     */
    public Ellipse(double radiusX, double radiusY, Color color){
        ellipse = new javafx.scene.shape.Ellipse(radiusX, radiusY);
        setColor(color);
    }

    /**
     * get the radius of the ellipse in the x direction
     * @return the radius of the ellipse in the x direction
     */
    public double getRadiusX(){
        return ellipse.getRadiusX();
    }

    /**
     * get the radius of the ellipse in the y direction
     * @return the radius of the ellipse in the y direction
     */
    public double getRadiusY() {
        return ellipse.getRadiusY();
    }

    /**
     * set the radius of the ellipse in the x direction
     * @param radiusX the new radius of the ellipse in the x direction
     */
    public void setRadiusX(double radiusX){
        ellipse.setRadiusX(radiusX);
    }

    /**
     * set the radius of the ellipse in the y direction
     * @param radiusY the new radius of the ellipse in the y direction
     */
    public void setRadiusY(double radiusY) {
        ellipse.setRadiusY(radiusY);
    }

    /**
     * set the color of the ellipse
     * @param color the new color of the ellipse
     */
    public void setColor(Color color){
        ellipse.setFill(color.getFXColor());
    }

    @Override
    public javafx.scene.shape.Shape getFXShape() {
        return ellipse;
    }

    @Override
    public void resize(double width, double height) {
        setRadiusX(width/2);
        setRadiusY(height/2);
    }

    @Override
    public void setLayout(double x, double y) {
        this.getNode().setLayoutX(x + getRadiusX());
        this.getNode().setLayoutY(y + getRadiusY());
    }
}

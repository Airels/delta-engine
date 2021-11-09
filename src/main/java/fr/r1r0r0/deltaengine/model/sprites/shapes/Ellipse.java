package fr.r1r0r0.deltaengine.model.sprites.shapes;

import javafx.scene.paint.Color;

/**
 * TODO
 */
public class Ellipse extends Shape{

    private javafx.scene.shape.Ellipse ellipse;

    /**
     * TODO
     * @param radiusX
     * @param radiusY
     * @param color
     */
    public Ellipse(double radiusX, double radiusY, Color color){
        ellipse = new javafx.scene.shape.Ellipse(radiusX, radiusY);
        setColor(color);
    }

    /**
     * TODO
     * @return
     */
    public double getRadiusX(){
        return ellipse.getRadiusX();
    }

    /**
     * TODO
     * @return
     */
    public double getRadiusY() {
        return ellipse.getRadiusY();
    }

    /**
     * TODO
     * @param radiusX
     */
    public void setRadiusX(double radiusX){
        ellipse.setRadiusX(radiusX);
    }

    /**
     * TODO
     * @param radiusY
     */
    public void setRadiusY(double radiusY) {
        ellipse.setRadiusY(radiusY);
    }

    /**
     * TODO
     * @param color
     */
    public void setColor(Color color){
        ellipse.setFill(color);
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
}

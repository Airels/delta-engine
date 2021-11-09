package fr.r1r0r0.deltaengine.model.sprites.shapes;

import javafx.scene.paint.Color;

public class Ellipse extends Shape{

    private javafx.scene.shape.Ellipse ellipse;

    public Ellipse(double radiusX, double radiusY, Color color){
        ellipse = new javafx.scene.shape.Ellipse(radiusX, radiusY);
        setColor(color);
    }

    public double getRadiusX(){
        return ellipse.getRadiusX();
    }

    public double getRadiusY() {
        return ellipse.getRadiusY();
    }

    public void setRadiusX(double radiusX){
        ellipse.setRadiusX(radiusX);
    }

    public void setRadiusY(double radiusY) {
        ellipse.setRadiusY(radiusY);
    }

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

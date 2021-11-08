package fr.r1r0r0.deltaengine.model.sprites.shapes;

import javafx.scene.paint.Color;

public class Circle extends Shape{

    private javafx.scene.shape.Circle circle;

    public Circle(double radius){
        circle = new javafx.scene.shape.Circle(radius);
    }

    public double getRadius(){
        return circle.getRadius();
    }

    public void setRadius(double radius){
        circle.setRadius(radius);
    }

    public void setColor(Color color){
        circle.setFill(color);
    }

    @Override
    public javafx.scene.shape.Shape getFXShape() {
        return circle;
    }
}

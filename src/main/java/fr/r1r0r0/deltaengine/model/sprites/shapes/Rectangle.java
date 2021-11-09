package fr.r1r0r0.deltaengine.model.sprites.shapes;

import javafx.scene.paint.Color;

public final class Rectangle extends Shape {

    private double width, height;
    private final javafx.scene.shape.Rectangle fxRectangle;

    public Rectangle(Color color) {
        this(0, 0, color);
    }

    public Rectangle(double width, double height, Color color) {
        this.width = width;
        this.height = height;
        this.fxRectangle = new javafx.scene.shape.Rectangle();

        fxRectangle.setWidth(width);
        fxRectangle.setHeight(height);
        fxRectangle.setFill(color);
    }

    public void setColor(Color color){
        fxRectangle.setFill(color);
    }

    @Override
    public javafx.scene.shape.Shape getFXShape() {
        return fxRectangle;
    }

    @Override
    public void resize(double width, double height) {
        fxRectangle.setWidth(width);
        fxRectangle.setHeight(height);
    }
}

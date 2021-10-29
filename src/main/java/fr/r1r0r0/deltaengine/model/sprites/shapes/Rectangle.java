package fr.r1r0r0.deltaengine.model.sprites.shapes;

public final class Rectangle extends Shape {

    private double width, height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public javafx.scene.shape.Shape getFXShape() {
        javafx.scene.shape.Rectangle fxRectangle = new javafx.scene.shape.Rectangle();

        fxRectangle.setWidth(width);
        fxRectangle.setHeight(height);

        return fxRectangle;
    }
}

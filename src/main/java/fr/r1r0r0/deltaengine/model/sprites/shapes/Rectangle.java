package fr.r1r0r0.deltaengine.model.sprites.shapes;

import fr.r1r0r0.deltaengine.view.colors.Color;

/**
 * Rectangle Sprite
 */
public class Rectangle extends Shape {
    private final javafx.scene.shape.Rectangle fxRectangle;

    /**
     * Instantiate the Rectangle Sprite
     * @param color of the Rectangle
     */
    public Rectangle(Color color) {
        this(1,1,color);
    }

    /**
     * Instantiate the Rectangle Sprite
     * @param width of the Rectangle
     * @param height of the Rectangle
     * @param color of the Rectangle
     */
    public Rectangle(double width, double height, Color color) {
        this.fxRectangle = new javafx.scene.shape.Rectangle();
        fxRectangle.setWidth(width);
        fxRectangle.setHeight(height);
        fxRectangle.setFill(color.getFXColor());
    }

    /**
     * set the color of the Rectangle
     * @param color the new color of the Rectangle
     */
    public void setColor(Color color){
        fxRectangle.setFill(color.getFXColor());
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

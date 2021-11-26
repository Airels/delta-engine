package fr.r1r0r0.deltaengine.model.sprites.shapes;

import fr.r1r0r0.deltaengine.view.colors.Color;

/** TODO implementé shape et manipuler une ellipse de javafx
 * Circle Sprite
 */
public class Circle extends Shape {
    private Ellipse ellipse;
    private double sizeScalar;

    /**
     * Instantiate an Image Sprite with a color and a radius
     * @param radius of the circle
     * @param sizeScalar optional sizeScalar
     * @param color of the circle
     */
    public Circle(double radius, double sizeScalar, Color color) {
        this.ellipse = new Ellipse(radius,radius,color);
        this.sizeScalar = sizeScalar;
    }

    public Circle(double radius, Color color) {
        this(radius,1,color);
    }

    /**
     * Second constructor used if Circle is an Entity
     * @param color of the circle
     */
    public Circle(Color color) {
        this(1, color);
    }

    public void setRadius(double radius){
        ellipse.setRadiusX(radius);
        ellipse.setRadiusY(radius);
    }

    public double getRadius(){
        return ellipse.getRadiusX();
    }

    @Override
    public void resize(double width, double height) {
        double diameter = Math.max(width, height);
        setRadius(diameter/2);
    }

    @Override
    public javafx.scene.shape.Shape getFXShape() {
        return ellipse.getFXShape();
    }

    @Override
    public void setLayout(double x, double y) {
        this.getNode().setLayoutX(x + getRadius());
        this.getNode().setLayoutY(y + getRadius());
    }

    @Override
    public void setSizeScalar(double scalar) {
        this.sizeScalar = scalar;
    }

    @Override
    public double getSizeScalar() {
        return sizeScalar;
    }

}

package fr.r1r0r0.deltaengine.model.sprites.shapes;

import fr.r1r0r0.deltaengine.view.colors.Color;

/** TODO implementé shape et manipuler une ellipse de javafx
 * Circle Sprite
 */
public class Circle extends Shape {
    private Ellipse ellipse;

    /**
     * Instantiate an Image Sprite with a color and a radius
     * @param radius of the circle
     * @param color of the circle
     */
    public Circle(double radius, Color color) {
        this.ellipse = new Ellipse(radius,radius,color);
    }

    @Override
    public void resize(double width, double height) {

    }

    @Override
    public javafx.scene.shape.Shape getFXShape() {
        return ellipse.getFXShape();
    }
}

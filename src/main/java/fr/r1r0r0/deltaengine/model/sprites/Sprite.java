package fr.r1r0r0.deltaengine.model.sprites;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Scale;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * The Sprite is the graphical component of an element,
 * it can be displayed through it's node
 */
public interface Sprite {
    /**
     * The Sprite object
     * @deprecated avoid using this elsewhere than in Engine
     * @return the Sprite object
     */
    Node getNode();

    /**
     * resize the sprite to the following width and height
     *
     * @param width
     * @param height
     */
    void resize(double width, double height);

    /**
     * Set the component's position
     *
     * @param x position
     * @param y position
     */
    default void setLayout(double x, double y) {
        this.getNode().setLayoutX(x);
        this.getNode().setLayoutY(y);
    }

    default void setLayout(Coordinates<Double> coordinates) {
        setLayout(coordinates.getX(), coordinates.getY());
    }

    /**
     * Set the component's scale
     * @param scaleX x scale
     * @param scaleY y scale
     */
    default void setScale(double scaleX, double scaleY) {
        this.getNode().setScaleX(scaleX);
        this.getNode().setScaleY(scaleY);
    }

    /**
     * Return the scale used for the component
     * @return the scale of the graphical element
     */
    default Scale getScale() {
        return new Scale(this.getNode().getScaleX(),
                this.getNode().getScaleY());
    }

    /**
     * Return the component's size
     * @return size's dimensions
     */
    default Dimension getSize() {
        Bounds b = this.getNode().getBoundsInLocal();
        return new Dimension(b.getWidth(), b.getHeight());
    }

    /**
     * Return the component's position (relative to its parent)
     * @return position's coordinates
     */
    default Coordinates<Double> getLayout() {
        Bounds b = this.getNode().getLayoutBounds();
        return new Coordinates<>(b.getWidth(), b.getHeight());
    }

    /**
     * Return the angle of the component
     * @return angle value of the component
     */
    default double getRotate() {
        return this.getNode().getRotate();
    }

    /**
     * Set the angle at which the component is rotated
     * @param angle component's new rotation angle
     */
    default void setRotate(double angle) {
        this.getNode().setRotate(angle);
    }

    /**
     * The depth of the component
     * @return the depth of the sprite
     */
    default double getZOrder() {
        return this.getNode().getViewOrder();
    }

    /**
     * Update the order or depth of the component
     * @param z is the new depth
     */
    default void setZOrder(double z) {
        this.getNode().setViewOrder(z);
    }
}

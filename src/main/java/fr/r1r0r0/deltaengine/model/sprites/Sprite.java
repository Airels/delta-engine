package fr.r1r0r0.deltaengine.model.sprites;

import javafx.scene.Node;

/**
 * The Sprite is the graphical component of an element,
 * it can be displayed through it's node
 */
public interface Sprite{
    /**
     * The Sprite object
     * @return the Sprite object
     */
    Node getNode();

    /**
     * resize the sprite to the following width and height
     * @param width
     * @param height
     */
    void resize(double width, double height);

    /**
     * Set the graphical position
     * @param x position
     * @param y position
     */
    default void setLayout(double x, double y){
        this.getNode().setLayoutX(x);
        this.getNode().setLayoutY(y);
    }

    /**
     * Update the order or depth of the sprite
     * @param z is the new depth
     */
    default void setZOrder(double z){
        this.getNode().setViewOrder(z);
    };

    /**
     * The depth of the sprite
     * @return the depth of the sprite
     */
    default double getZOrder(){
        return this.getNode().getViewOrder();
    }
}

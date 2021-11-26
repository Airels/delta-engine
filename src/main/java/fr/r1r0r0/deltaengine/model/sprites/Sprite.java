package fr.r1r0r0.deltaengine.model.sprites;

import fr.r1r0r0.deltaengine.model.Coordinates;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * The Sprite is the graphical component of an element,
 * it can be displayed through it's node
 */
public interface Sprite {

    Sprite INVISIBLE_SPRITE = InvisibleSprite.getInstance();

    /**
     * The jfx Node object
     * @deprecated avoid using this elsewhere than in Engine
     * @return the Sprite object
     */
    Node getNode();

    /**
     * Resize component's width, also resize height if
     * we want to preserve scale
     * @param width new width
     * @param keepScale to tell if we want to preserve scale
     */
    default void resizeWidth(double width, boolean keepScale){
        double ratio = getSizes().getHeight()/ getSizes().getWidth();
        if (keepScale || !canDeform()) resize(width,width*ratio);
        else resize(width, getSizes().getHeight());
    }

    /**
     * Resize component's height, also resize width if
     * we want to preserve scale
     * @param height new width
     * @param keepScale to tell if we want to preserve scale
     */
    default void resizeHeight(double height, boolean keepScale){
        double ratio = getSizes().getWidth()/ getSizes().getHeight();
        if (keepScale || !canDeform()) resize(height*ratio,height);
        else resize(getSizes().getHeight(),height);
    }

    /**
     * Resize component's width, preserving the scale
     * @param width
     */
    default void resizeWidth(double width){
        resizeWidth(width,true);
    }

    /**
     * Resize component's height, preserving the scale
     * @param height
     */
    default void resizeHeight(double height){
        resizeHeight(height,true);
    }

    /**
     * Resize the component's biggest bound,
     * preserve the component's scale
     * @param maxSize
     */
    default void resizeMaxBound(double maxSize){
        if (getSizes().getWidth() > getSizes().getHeight()){
            resizeWidth(maxSize);
        }else {
            resizeHeight(maxSize);
        }
    }

    /**
     * Resize the component's smallest bound,
     * preserve the component's scale
     * @param minSize
     */
    default void resizeMinBound(double minSize){
        if (getScale().getScaleX() > getScale().getScaleY()){
            resizeHeight(minSize);
        }else {
            resizeWidth(minSize);
        }
    }

    /**
     * Resize the component to the following width and height
     * DO NOT preserve the component's scale
     * @param width
     * @param height
     */
    default void resize(double width, double height){

        if (canDeform()) getNode().resize(width,height);
        else {
            Double bRatio = height/width;
            Double cRatio = getSizes().getHeight()/getSizes().getWidth();
            boolean isSameRatio = Math.abs(bRatio - cRatio) < 0.0001;
            if (isSameRatio) getNode().resize(width,height);
        }
    }

    /**
     * Resize the component to fit bounds width and height
     * DO preserve the component's scale
     * @param width
     * @param height
     */
    default void resizeKeepScale(double width, double height){
        double bFatNess = height/width;
        double cFatNess = getSizes().getHeight()/getSizes().getWidth();
        if (bFatNess>cFatNess) resizeWidth(width);
        else resizeHeight(height);
    }

    /**
     * Return the component's sizes
     * @return size's dimensions
     */
    default Sizes getSizes() {
        Bounds b = this.getNode().getBoundsInLocal();
        return new Sizes(b.getWidth(), b.getHeight());
    }

    /**
     * Set the component's position,
     * component origin is located at the top left
     * @param x position
     * @param y position
     */
    default void setLayout(double x, double y) {
        getNode().setLayoutX(x);
        getNode().setLayoutY(y);
    }

    default void setLayout(Coordinates<Double> coordinates) {
        setLayout(coordinates.getX(), coordinates.getY());
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
     * Set the component's scale
     * transformation applied from the center of the bounding box
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
     * Set the angle at which the component is rotated
     * @param angle component's new rotation angle
     */
    default void setRotate(double angle) {
        this.getNode().setRotate(angle);
    }

    /**
     * Return the angle of the component
     * @return angle value of the component
     */
    default double getRotate() {
        return this.getNode().getRotate();
    }

    /**
     * Tell if the component can be deformed,
     * there if it can resize without non uniformly,
     * without keeping scale
     * @return component can be deformed non uniformly
     */
    default boolean canDeform(){
        return false;
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

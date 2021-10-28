package model.elements.entities;

import model.Coordinates;
import model.Direction;
import model.elements.Element;
import model.sprites.Sprite;

public class Entity implements Element {
    @Override
    public Sprite getSprite() {
        return null;
    }

    @Override
    public void setSprite(Sprite sprite) {

    }

    @Override
    public Coordinates getCoordinates() {
        return null;
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {

    }

    @Override
    public String getName() {
        return null;
    }

    public Direction getDirection() {
        return null;
    }

    public void setDirection(Direction direction) {

    }

    public double getSpeed() {
        return 0;
    }

    public void setSpeed(double speed) {

    }
}

package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void testCollide() {
        Entity entity1 = new Entity(null, new Coordinates(0, 0), new Sprite() {
            @Override
            public Node getNode() {
                return new Rectangle();
            }

            @Override
            public void resize(double width, double height) {

            }
        }, new Dimension(1, 1));
        Entity entity2 = new Entity(null, new Coordinates(10, 10), new Sprite() {
            @Override
            public Node getNode() {
                return new Rectangle();
            }

            @Override
            public void resize(double width, double height) {

            }
        }, new Dimension(1, 1));
        Assertions.assertTrue(entity1.testCollide(entity1));
        Assertions.assertFalse(entity1.testCollide(entity2));

    }
}
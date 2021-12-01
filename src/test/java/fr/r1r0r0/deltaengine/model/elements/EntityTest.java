package fr.r1r0r0.deltaengine.model.elements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.InvisibleSprite;

class EntityTest {

    @Test
    void testCollide() {
        Entity entity1 = new Entity(null, new Coordinates(0., 0.),
                InvisibleSprite.getInstance(), new Dimension(1, 1));
        Entity entity2 = new Entity(null, new Coordinates(10.0, 10.0),
                InvisibleSprite.getInstance(), new Dimension(1, 1));
        Assertions.assertTrue(entity1.testCollide(entity1));
        Assertions.assertFalse(entity1.testCollide(entity2));

    }
}
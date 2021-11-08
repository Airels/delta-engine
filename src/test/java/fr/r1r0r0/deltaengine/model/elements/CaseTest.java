package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseTest {
    @Test
    public void test(){
        Case c = new Case(0,0, new Sprite() {
            @Override
            public Node getNode() {
                return null;
            }
        });

        assert c.getCoordinates().equals(new Coordinates(0,0));
        c.setCoordinates(new Coordinates(1,2));
        assert c.getCoordinates().equals(new Coordinates(1,2));


    }


}
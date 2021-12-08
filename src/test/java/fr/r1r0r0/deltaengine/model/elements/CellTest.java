package fr.r1r0r0.deltaengine.model.elements;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.CrossableCell;
import fr.r1r0r0.deltaengine.model.sprites.InvisibleSprite;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Node;
import org.junit.jupiter.api.Test;

class CellTest {
    @Test
    public void test(){
        Cell c = new CrossableCell(0,0, InvisibleSprite.getInstance());

        assert c.getCoordinates().equals(new Coordinates(0,0));
        c.setCoordinates(new Coordinates(1,2));
        assert c.getCoordinates().equals(new Coordinates(1,2));


    }


}
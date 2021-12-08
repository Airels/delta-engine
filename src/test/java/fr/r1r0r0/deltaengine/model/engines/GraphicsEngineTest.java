package fr.r1r0r0.deltaengine.model.engines;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedDeque;

import fr.r1r0r0.deltaengine.model.elements.Element;

class GraphicsEngineTest {
    GraphicsEngine graphicsEngine;
    Element e1,e2;
    ConcurrentLinkedDeque<Element> elements;
    Field mapField, elementField;
    /*
    @BeforeEach
    void testInit() throws NoSuchFieldException {
        graphicsEngine = new GraphicsEngine();
        Coordinates<Double> coords = new Coordinates<>(1.0,1.0);
        Dimension dim = new Dimension(1,1);
        e1 = new Entity("bonjour",coords, InvisibleSprite.getInstance(),dim);
        e2 = new Entity("bonjour",coords, InvisibleSprite.getInstance(),dim);
        Stage stage = new Stage();
        graphicsEngine.setStage(stage);
        graphicsEngine.init();
        elementField = graphicsEngine.getClass().getDeclaredField("elements");
        elementField.setAccessible(true);
        mapField = graphicsEngine.getClass().getDeclaredField("mapLevel");
        mapField.setAccessible(true);
    }


    @Test
    void setMap() throws IllegalAccessException {

        MapLevel mapLevel = new MapLevelBuilder("map",5,5).build();

        graphicsEngine.setMap(mapLevel);


        MapLevel mapLevel2 = new MapLevelBuilder("map2",6,6).build();
        graphicsEngine.setMap(mapLevel2);

        assertEquals(mapLevel2,mapField.get(mapLevel));
    }

    @Test
    void addElement() throws IllegalAccessException {


        graphicsEngine.addElement(e1);

        elements = (ConcurrentLinkedDeque<Element>) elementField.get(graphicsEngine);
        assertEquals(elements.size(),1);
        assertTrue(elements.contains(e1));

        graphicsEngine.addElement(e2);

        elements = (ConcurrentLinkedDeque<Element>) elementField.get(graphicsEngine);
        assertEquals(elements.size(),2);
        assertTrue(elements.contains(e2));




    }

    @Test
    void removeElement() throws IllegalAccessException {

        graphicsEngine.addElement(e1);
        graphicsEngine.addElement(e2);
        elements = (ConcurrentLinkedDeque<Element>) elementField.get(graphicsEngine);
        assertEquals(elements.size(),2);

        graphicsEngine.removeElement(e1);
        assertEquals(elements.size(),1);
        graphicsEngine.removeElement(e2);
        assertEquals(elements.size(),0);

    }

    @Test
    void clearMap() throws IllegalAccessException {


        MapLevel mapLevel = new MapLevelBuilder("map",5,5).build();
        graphicsEngine.setMap(mapLevel);

        assertEquals(mapLevel, mapField.get(mapLevel));
        graphicsEngine.clearMap();



    }

    @Test
    void addHudElement() {
    }

    @Test
    void clearElements() {
    }

    @Test
    void setStageIcon() {
    }

    @Test
    void setStage() {
    }

     */
}
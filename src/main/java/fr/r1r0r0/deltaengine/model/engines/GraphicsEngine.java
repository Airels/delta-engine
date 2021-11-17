package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.elements.*;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.*;

/**
 * Graphic engine takes care of maintaining the view updated
 */
final class GraphicsEngine implements Engine {

    private ArrayList<Element> elements;
    private Map<Element, Sprite> elementSpriteMap;
    private double caseSize = 40;
    private double offsetX = 0.0, offsetY = 0.0;
    private MapLevel mapLevel;
    private Stage stage;
    private Pane root;
    private Scene scene;
    private boolean initialized;
    private boolean started;

    private double widthMargin, heightMargin;

    /**
     * Constructor to instantiate the engine but not initialising its components
     */
    public GraphicsEngine() {
        initialized = false;
        started = false;
    }

    /**
     * initialising the graphic engine components
     */
    @Override
    public void init() {
        elementSpriteMap = new HashMap<>();
        elements = new ArrayList<>();
        mapLevel = null;

        int initialWidth = 1, initialHeight = 1;

        root = new Pane();
        scene = new Scene(root, initialWidth, initialHeight);
        root.setVisible(true);
        root.setStyle("-fx-background-color: #000000");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        try {
            setStageIcon(new Image(getClass().getResourceAsStream("/icon.png")));
        }catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        stage.show();

        widthMargin = stage.getWidth() - initialWidth-1;
        heightMargin = stage.getHeight() - initialHeight-1;

        stage.setFullScreen(true);

        initialized = true;
    }

    /**
     * Graphic engine loop
     */
    @Override
    public void run() {
        if (started) throw new RuntimeException("Graphic Engine is already running");
        started = true;

        if (mapLevel != null){
            for (Cell c:mapLevel.getBufferedCells().getAndResetAddedElements())
                addElement(c);
            for (Cell c:mapLevel.getBufferedCells().getAndResetRemovedElements())
                removeElement(c);

            for (Element e:mapLevel.getBufferedEntities().getAndResetAddedElements())
                addElement(e);
            for (Element e:mapLevel.getBufferedEntities().getAndResetRemovedElements())
                removeElement(e);
        }

        for (Element e : elements) {
            updateElement(e);
        }
        started = false;

    }

    /**
     * Update an element's graphical view
     *
     * @param e the element to be updated
     */
    private void updateElement(Element e) {
        if (!elements.contains(e)) throw new NoSuchElementException();

        Sprite oldSprite = elementSpriteMap.get(e);
        Sprite newSprite = e.getSprite();

        if (newSprite != oldSprite) {
            root.getChildren().remove(oldSprite.getNode());
            root.getChildren().add(newSprite.getNode());
            elementSpriteMap.put(e, newSprite);
        }

        newSprite.setLayout(offsetX + e.getCoordinates().getX().doubleValue() * caseSize,
                offsetY + e.getCoordinates().getY().doubleValue() * caseSize);


    }

    /**
     * Set the map to be diplayed, replace the old map
     * @param mapLevel map to be shown
     */
    public void setMap(MapLevel mapLevel) {
        if (this.mapLevel != null){
            for (Element e:this.mapLevel.getCells()) removeElement(e);
            for (Element e:this.mapLevel.getEntities()) removeElement(e);
        }

        this.mapLevel = mapLevel;
        fitMapToStage();

        for (Cell c : mapLevel.getCells()) {
            addElement(c);
        }

        for (Entity element : mapLevel.getEntities()) {
            addElement(element);
        }
    }

    /**
     * fit the map to the screen,
     * caseSize is calculated to fit exactly,
     * the offset are calculated to center the elements
     */
    private void fitMapToStage() {
        double caseSizeWidth = stage.getWidth() / mapLevel.getWidth();
        double caseSizeHeight = stage.getHeight() / mapLevel.getHeight();
        caseSize = Math.min(caseSizeWidth, caseSizeHeight);

        if (caseSizeHeight>caseSizeWidth){
            offsetY = (stage.getHeight() - mapLevel.getHeight()*caseSize)/2 - heightMargin;
        }
        else {
            offsetX = (stage.getWidth()- mapLevel.getWidth()*caseSize)/2 - widthMargin;
        }
        stage.setTitle(mapLevel.getName());
    }

    /**
     * add an element to display
     * element is resized according to CASE_SIZE
     * @param element element to display
     */
    public void addElement(Element element) {
        if (elements.contains(element)) {
            removeElement(element);
        }
        elementSpriteMap.put(element, element.getSprite());
        elements.add(element);
        root.getChildren().add(element.getSprite().getNode());
        element.getSprite().resize(
                    caseSize * element.getDimension().getWidth(),
                    caseSize * element.getDimension().getHeight());
        updateElement(element);
    }

    /**
     * add an element to display
     * element is NOT resized
     * @param element element to display
     */
    public void addHudElement(Element element){
        if (elements.contains(element)) {
            removeElement(element);
        }
        elementSpriteMap.put(element, element.getSprite());
        elements.add(element);
        root.getChildren().add(element.getSprite().getNode());
        updateElement(element);
    }

    /**
     * remove an element from the graphic engine and from being displayed
     *
     * @param element an element from the graphic engine
     */
    public void removeElement(Element element) {
        elements.remove(element);
        elementSpriteMap.remove(element);
        root.getChildren().remove(element.getSprite().getNode());
    }

    /**
     * Empty the all element from the graphic engine and from the view
     */
    public void clearElements() {
        for (Element e:elements) removeElement(e);
        elements.clear();
        elementSpriteMap.clear();
        root.getChildren().clear();
    }

    /**
     * set the icon used for the engine's
     * @param image icon used
     */
    public void setStageIcon(Image image){
        stage.getIcons().clear();
        stage.getIcons().add(image);
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }
}

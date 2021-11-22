package engines.kernel;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.CrossableCell;
import fr.r1r0r0.deltaengine.model.elements.cells.default_cells.Wall;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;


public class Main2 {

    public static void main(String[] args) throws Exception {
        KernelEngine deltaEngine = DeltaEngine.launch();
        deltaEngine.setFrameRate(60);
        deltaEngine.printFrameRate(true);


        MapLevel mapLevel;
        //mapLevel = createMapLevelDamier("mapTest", 10,10);
        mapLevel = createMapLevelPrison("mapTest", 10, 10, 6, 8, 6, 8);
        mapLevel.replaceCell(new Wall(7, 7));
        deltaEngine.addMap(mapLevel);

        /*
        Entity pacman = new Entity("pacman", new Coordinates<>(5., 5.), new Circle(1, Color.YELLOW), new Dimension(1, 1));
        pacman.setSpeed(0);
        mapLevel.addEntity(pacman);
        */

        Entity red = new Entity("red", new Coordinates<>(8., 8.), new Rectangle(0.5, 0.5, Color.RED), new Dimension(0.8, 0.8)) {
            Sprite p1 = new Rectangle(0.5, 0.5, Color.GREEN);
            Sprite p2 = new Rectangle(0.5, 0.5, Color.RED);
            @Override
            public Sprite getSprite () {
                return (getCoordinates().getX() < 7) ? p1 : p2;
            }
        };
        red.setSpeed(0);
        mapLevel.addEntity(red);

        /*
        InputEvent moveUpEvent = new InputEvent(new ChangeMove(pacman, Direction.UP), new ChangeMove(pacman, Direction.IDLE));
        InputEvent moveDownEvent = new InputEvent(new ChangeMove(pacman, Direction.DOWN), new ChangeMove(pacman, Direction.IDLE));
        InputEvent moveLeftEvent = new InputEvent(new ChangeMove(pacman, Direction.LEFT), new ChangeMove(pacman, Direction.IDLE));
        InputEvent moveRightEvent = new InputEvent(new ChangeMove(pacman, Direction.RIGHT), new ChangeMove(pacman, Direction.IDLE));
        deltaEngine.setInput(Key.Z, moveUpEvent);
        deltaEngine.setInput(Key.Q, moveLeftEvent);
        deltaEngine.setInput(Key.S, moveDownEvent);
        deltaEngine.setInput(Key.D, moveRightEvent);
        */

        InputEvent moveUpEventRed = new InputEvent(new ChangeMove(red, Direction.UP), new ChangeMove(red, Direction.IDLE));
        InputEvent moveDownEventRed = new InputEvent(new ChangeMove(red, Direction.DOWN), new ChangeMove(red, Direction.IDLE));
        InputEvent moveLeftEventRed = new InputEvent(new ChangeMove(red, Direction.LEFT), new ChangeMove(red, Direction.IDLE));
        InputEvent moveRightEventRed = new InputEvent(new ChangeMove(red, Direction.RIGHT), new ChangeMove(red, Direction.IDLE));
        deltaEngine.setInput(Key.ARROW_UP, moveUpEventRed);
        deltaEngine.setInput(Key.ARROW_LEFT, moveLeftEventRed);
        deltaEngine.setInput(Key.ARROW_DOWN, moveDownEventRed);
        deltaEngine.setInput(Key.ARROW_RIGHT, moveRightEventRed);

        InputEvent exitEvent = new InputEvent(() -> System.exit(0), null);
        deltaEngine.setInput(Key.ESCAPE, exitEvent);
        InputEvent pauseEvent = new InputEvent(deltaEngine::haltCurrentMap, deltaEngine::resumeCurrentMap);
        deltaEngine.setInput(Key.P, pauseEvent);

        deltaEngine.setCurrentMap("mapTest");

        try {

            for (; ; Thread.sleep(1000)) {
                //
            }

        } catch (Exception e) {

        }
    }

    private static MapLevel createMapLevelDamier(String name, int width, int height) {
        //TODO: faire une erreur generique pour les MapLevel, qui se subdivise en toutes les erreurs actuels
        MapLevelBuilder mapLevelBuilder = new MapLevelBuilder(name, width, height);
        try {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    mapLevelBuilder.setCell(
                            new CrossableCell(i, j, new Rectangle(((i + j) & 1) == 0 ? Color.BLACK : Color.WHITE))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapLevelBuilder.build();
    }

    private static MapLevel createMapLevelPrison(String name, int width, int height,
                                                 int prisonXMin, int prisonXMax,
                                                 int prisonYMin, int prisonYMax) {
        MapLevelBuilder mapLevelBuilder = new MapLevelBuilder(name, width, height);
        try {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Cell cell;
                    if (prisonXMin <= i && i <= prisonXMax
                            && prisonYMin <= j && j <= prisonYMax) {
                        cell = new CrossableCell(i, j, new Rectangle(Color.WHITE));
                    } else {
                        cell = new Wall(i, j);
                    }
                    mapLevelBuilder.setCell(cell);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapLevelBuilder.build();
    }


    public static class ChangeMove implements Trigger {

        private Direction direction;
        private Entity e;

        public ChangeMove(Entity e, Direction direction) {
            this.direction = direction;
            this.e = e;
        }

        @Override
        public void trigger() {
            e.setSpeed(1);
            e.setDirection(direction);
        }
    }

}


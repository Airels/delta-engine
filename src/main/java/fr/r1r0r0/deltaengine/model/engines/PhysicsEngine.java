package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.elements.Case;
import fr.r1r0r0.deltaengine.model.elements.CrossableVisitor;
import fr.r1r0r0.deltaengine.model.elements.Entity;

class PhysicsEngine implements Engine {

    private Map map;
    private long previousRunTime;

    public PhysicsEngine () {
        map = null;
        previousRunTime = System.currentTimeMillis();
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        long currentRunTime = System.currentTimeMillis();
        long deltaTime = previousRunTime - currentRunTime;
        previousRunTime = currentRunTime;
        if (map != null) {
            for (Entity entity : map.getEntities()) {
                updateCoordinate(entity,deltaTime);
                entity.setDirection(Direction.IDLE);
            }
        }
    }

    private void updateCoordinate (Entity entity, long deltaTime) {
        Coordinates nextCoordinate = entity.getCoordinates().getNextCoordinates(entity.getDirection(),
                entity.getSpeed()*deltaTime);
        if (isValidCoordinates(nextCoordinate)) entity.setCoordinates(nextCoordinate);
    }

    private boolean isValidCoordinates (Coordinates coordinates) {
        int x = (coordinates.getX() >= 0) ? (int) coordinates.getX() : ((int) coordinates.getX() + 1);
        int y = (coordinates.getY() >= 0) ? (int) coordinates.getY() : ((int) coordinates.getY() + 1);
        Case nextCase = map.getCase(x,y);
        return nextCase != null && CrossableVisitor.isCaseCrossable(nextCase);
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public void clearMap() {
        map = null;
    }

}

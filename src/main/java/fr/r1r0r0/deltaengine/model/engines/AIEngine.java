package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.AI;

import java.util.ArrayList;
import java.util.List;

class AIEngine implements Engine {

    private final List<AI> ais;

    AIEngine() {
        ais = new ArrayList<>();
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        synchronized (this) {
            ais.forEach(AI::tick);
        }
    }

    public synchronized void addAI(AI ai) {
        ais.add(ai);
    }

    public synchronized void removeAI(AI ai) {
        ais.remove(ai);
    }

    public synchronized void clearAIs() {
        ais.clear();
    }
}

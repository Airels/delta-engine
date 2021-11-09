package fr.r1r0r0.deltaengine.model.engines;

import fr.r1r0r0.deltaengine.model.AI;

import java.util.ArrayList;
import java.util.List;

/**
 * AI Engine. Processing all AI's code when run() is called.
 */
final class AIEngine implements Engine {

    private final List<AI> ais;

    /**
     * Default constructor
     */
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

    /**
     * Add a new AI to process
     *
     * @param ai AI to add
     */
    public synchronized void addAI(AI ai) {
        ais.add(ai);
    }

    /**
     * Remove an AI from the engine
     *
     * @param ai AI to remove
     */
    public synchronized void removeAI(AI ai) {
        ais.remove(ai);
    }

    /**
     * Remove all AI's of the Engine
     */
    public synchronized void clearAIs() {
        ais.clear();
    }
}

package fr.r1r0r0.deltaengine.model.engines;

/**
 * Engine implementation. Used by all Engine's that Kernel use.
 */
interface Engine extends Runnable {

    /**
     * Initialize the Engine.
     */
    void init();
}

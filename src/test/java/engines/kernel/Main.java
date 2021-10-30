package engines.kernel;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;

public class Main {

    public final static KernelEngine deltaEngine = new KernelEngine();

    public static void main(String[] args) {
        System.out.println("LAUNCHING");
        deltaEngine.launch();
        System.out.println("LAUNCHED");
    }
}

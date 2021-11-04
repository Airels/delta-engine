package engines.kernel;

import fr.r1r0r0.deltaengine.model.Sound;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.SoundEngine;

public class Main {

    public static void main(String[] args) throws Exception {
        KernelEngine deltaEngine = DeltaEngine.launch();
        deltaEngine.printFrameRate(true);
        deltaEngine.setFrameRate(100_000);
    }
}

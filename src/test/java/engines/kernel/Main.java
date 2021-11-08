package engines.kernel;

import fr.r1r0r0.deltaengine.model.Map;
import fr.r1r0r0.deltaengine.model.Sound;
import fr.r1r0r0.deltaengine.model.elements.basic_cases.Wall;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.SoundEngine;

public class Main {

    public static void main(String[] args) throws Exception {
        KernelEngine deltaEngine = DeltaEngine.launch();
        deltaEngine.printFrameRate(false);

        Map map = new Map("test",30,20);
        deltaEngine.addMap(map);
        map = new Map("test2", 10,20);
        map.addCase(new Wall(2,2));
        deltaEngine.addMap(map);

        deltaEngine.setCurrentMap("test");

        Thread.sleep(2000);

        deltaEngine.setCurrentMap("test2");

        assert map.getCase(2, 2) instanceof Wall;
    }
}

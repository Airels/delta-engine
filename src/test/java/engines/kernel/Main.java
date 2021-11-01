package engines.kernel;

import fr.r1r0r0.deltaengine.model.Sound;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.SoundEngine;

public class Main {

    public static void main(String[] args) throws Exception {
        KernelEngine deltaEngine = DeltaEngine.launch();

        SoundEngine soundEngine = deltaEngine.getSoundEngine();
        Sound siren = new Sound("siren", "C:\\Users\\Yohan\\Desktop\\a.wav");

        soundEngine.addSound(siren);
        soundEngine.setLoop("siren", true);
        soundEngine.setVolume("siren", 1);
        soundEngine.setSpeed("siren", 1.1);
        soundEngine.play("siren");
    }
}

package engines.kernel;

import fr.r1r0r0.deltaengine.model.Sound;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.SoundEngine;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Main {

    public static void main(String[] args) throws Exception {
        /*
        KernelEngine deltaEngine = DeltaEngine.launch();

        SoundEngine soundEngine = deltaEngine.getSoundEngine();
        Sound siren = new Sound("siren", "C:\\Users\\Yohan\\Desktop\\final.wav");

        soundEngine.addSound(siren);
        soundEngine.setLoop("siren", true);
        soundEngine.setVolume("siren", 0.5);
        soundEngine.setSpeed("siren", 1);
        soundEngine.play("siren");

        MediaPlayer player = soundEngine.getMediaPlayer("siren");

        while (true) {
            if (player.getCurrentTime().equals(player.getStopTime()))
                player.seek(player.getStartTime());
        }

         */
        Sound siren = new Sound("siren", "C:\\Users\\Yohan\\Desktop\\final.wav");
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(siren.getFile());
        clip.open(inputStream);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.

        Thread.sleep(999999);
    }
}

package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private Clip clip;
    private URL soundURL[] = new URL[6];
    FloatControl fc;
    public int volumeScale = 3;
    public float volume;

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/331298__cebuana__oneminute.wav");
        soundURL[1] = getClass().getResource("/sound/slam.wav");
        soundURL[2] = getClass().getResource("/sound/obtain.wav");
        soundURL[3] = getClass().getResource("/sound/death.wav");
        soundURL[4] = getClass().getResource("/sound/damage.wav");
        soundURL[5] = getClass().getResource("/sound/background.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void checkVolume() {
        switch (volumeScale) {
            case 0 -> volume = -80F;
            case 1 -> volume = -20F;
            case 2 -> volume = -12F;
            case 3 -> volume = -5F;
            case 4 -> volume = 1F;
            case 5 -> volume = 6F;
        }
        fc.setValue(volume);
    }
}

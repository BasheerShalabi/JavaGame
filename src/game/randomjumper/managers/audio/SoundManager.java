package game.randomjumper.managers.audio;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

     private static Clip music;

    private static class AudioData {
        byte[] bytes;
        AudioFormat format;
        long frameLength;

        AudioData(byte[] bytes, AudioFormat format, long frameLength) {
            this.bytes = bytes;
            this.format = format;
            this.frameLength = frameLength;
        }
    }

    private static final Map<String, AudioData> audioCache = new HashMap<>();

    public static void preloadSounds() {
        preloadSound("nut","/sounds/nut.wav");
        preloadSound("hit","/sounds/hit.wav");
        preloadSound("jump","/sounds/jump.wav");
        preloadSound("doublejump","/sounds/doublejump.wav");
        preloadSound("platform-swap","/sounds/platform-swap.wav");
        preloadSound("powerup","/sounds/powerup.wav");
        preloadSound("music","/sounds/retro forest.wav");
        preloadSound("gameover","/sounds/gameover.wav");
        preloadSound("apple","/sounds/apple.wav");
        preloadSound("leaf","/sounds/leaf.wav");
    }

    public static void preloadSound(String key ,String path) {
        try {
            URL file = SoundManager.class.getResource(path);
            assert file != null;
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            AudioFormat format = ais.getFormat();
            long frameLength = ais.getFrameLength();
            byte[] bytes = ais.readAllBytes(); 
            ais.close();
            Clip warmup = AudioSystem.getClip();
            warmup.open(format, bytes, 0, bytes.length);
            warmup.start();
            warmup.stop();
            warmup.flush();
            warmup.close();
            audioCache.put(key, new AudioData(bytes, format, frameLength));
        } catch (Exception e) {
            System.err.println("Failed to load sound: " + path);
        }
    }

    public static void playClip(String key) {
        AudioData audioData = audioCache.get(key);
        if (audioData == null) {
            System.err.println("Sound not preloaded: " + key);
            return;
        }

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData.bytes);
            AudioInputStream ais = new AudioInputStream(bais, audioData.format, audioData.frameLength);

            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close(); // clean up resources when done
                }
            });

            clip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void playMusic() {
        AudioData audioData = audioCache.get("music");
        if (audioData == null) {
            System.err.println("Sound not preloaded: music");
            return;
        }

        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(audioData.bytes);
            AudioInputStream ais = new AudioInputStream(bais, audioData.format, audioData.frameLength);

            music = AudioSystem.getClip();
            music.open(ais);

            music.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    music.close(); // clean up resources when done
                }
            });

            FloatControl volume = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-15.0f);

            music.loop(9999);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void stopMusic(){
        music.stop();
        music.flush();
        music.close();
    }
}

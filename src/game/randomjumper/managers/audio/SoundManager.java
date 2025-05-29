package game.randomjumper.managers.audio;

import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

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
        preloadSound("nut","resources/sound/nut.wav");
        preloadSound("hit","resources/sound/hit.wav");
        preloadSound("jump","resources/sound/jump.wav");
        preloadSound("doublejump","resources/sound/doublejump.wav");
        preloadSound("platform-swap","resources/sound/platform-swap.wav");
    }

    public static void preloadSound(String key ,String path) {
        try {
            File file = new File(path);
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
}

package main;

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

    public static void preloadSound(String path) {
        try {
            File file = new File(path);
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            AudioFormat format = ais.getFormat();
            long frameLength = ais.getFrameLength();
            byte[] bytes = ais.readAllBytes(); 
            ais.close();

            audioCache.put(path, new AudioData(bytes, format, frameLength));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void playClip(String path) {
        AudioData audioData = audioCache.get(path);
        if (audioData == null) {
            System.err.println("Sound not preloaded: " + path);
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

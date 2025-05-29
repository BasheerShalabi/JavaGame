package game.randomjumper.managers.animation;

import game.randomjumper.managers.image.ImageManager;

import java.awt.image.BufferedImage;

public class SpriteAnimation {
    private final BufferedImage[] frames;
    private int frameIndex = 0;
    private long lastFrameTime;
    private final long frameDelay;

    public SpriteAnimation(BufferedImage[] frames, long frameDelay) {
        this.frames = frames;
        this.frameDelay = frameDelay;
        this.lastFrameTime = System.currentTimeMillis();
    }

    public void update() {
        long now = System.currentTimeMillis();
        if (now - lastFrameTime >= frameDelay) {
            frameIndex = (frameIndex + 1) % frames.length;
            lastFrameTime = now;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[frameIndex];
    }

    public void reset() {
        frameIndex = 0;
        lastFrameTime = System.currentTimeMillis();
    }

    public static SpriteAnimation loadAnimation(String name, int frameCount, long delay,String key) {
        BufferedImage[] frames = new BufferedImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = ImageManager.getImage(key + name + "-" + i);
        }
        return new SpriteAnimation(frames, delay);
    }
}

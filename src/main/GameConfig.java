package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//this class simply contains all the constants used in the game
public class GameConfig {

    private GameConfig() {}

    public static final BufferedImage BACKGROUND_IMAGE;
    public static final BufferedImage GROUND_IMAGE;
    public static final BufferedImage PLATFORM_IMAGE;
    public static final BufferedImage PROJECTILE;
    public static final BufferedImage PROJECTILE_ROTATED;

    public static final String COIN_COLLECT_SOUND = "resources/sound/coin.wav";
    public static final String PLAYER_HIT_SOUND = "resources/sound/hit.wav";
    public static final String PLAYER_JUMP_SOUND = "resources/sound/jump.wav";
    public static final String PLAYER_DOUBLE_JUMP_SOUND = "resources/sound/doublejump.wav";
    public static final String PLATFORM_SWAP_SOUND = "resources/sound/platform-swap.wav";

    static {
        try {
            BACKGROUND_IMAGE = ImageIO.read(new File("resources/images/background.png"));
            GROUND_IMAGE = ImageIO.read(new File("resources/images/ground.png"));
            PLATFORM_IMAGE = ImageIO.read(new File("resources/images/Grass Dirt Platform.png"));
            PROJECTILE = ImageIO.read(new File("resources/images/pixel-fire.png"));

            int w = PROJECTILE.getWidth();
            int h = PROJECTILE.getHeight();

            PROJECTILE_ROTATED = new BufferedImage(w, h, PROJECTILE.getType());
            Graphics2D g2d = PROJECTILE_ROTATED.createGraphics();

            // Rotate 180 degrees around the image center
            AffineTransform at = new AffineTransform();
            at.translate(w, h);
            at.rotate(Math.toRadians(180));

            g2d.drawImage(PROJECTILE, at, null);
            g2d.dispose();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 800;
    public static final int FRAME_TIME = 1000/60;

    public static final int PROJECTILE_MIN_FIRE_INTERVAL_MS = 1000;
    public static final int PROJECTILE_MAX_FIRE_INTERVAL_MS = 5000;
    public static final int PROJECTILE_FIRE_COOLDOWN_INTERVAL_MS = 4000;
    public static final int PROJECTILE_SPEED = 3;
    public static final int PROJECTILE_WIDTH = 20;
    public static final int PROJECTILE_HEIGHT = 10;

    public static final int PLATFORM_SPACING = 70;
    public static final int PLATFORM_START_Y = 620;
    public static final int PLATFORM_END_Y = 200;
    public static final int PLATFORM_MIN_X = 100;
    public static final int PLATFORM_MAX_X = 550;
    public static final int PLATFORM_WIDTH = 200;
    public static final int PLATFORM_HEIGHT = 20;

    public static final int PLAYER_HEIGHT = 40;
    public static final int PLAYER_WIDTH = 40;

    public static final int INITIAL_PLAYER_X = 500;
    public static final int INITIAL_PLAYER_Y = 200;

    public static final int GROUND_OFFSET = 100;
    public static final int GRAVITY = 1;
    public static final int MAX_DOWN_ACCELERATION = 10;
    public static final int GROUND_LEVEL = SCREEN_HEIGHT - GROUND_OFFSET;

    public static final int COIN_SPAWN_DELAY = 1000;
    public static final int COIN_SIZE = 20;
    public static final int COIN_OFFSET_X = 10;
    public static final int COIN_OFFSET_Y = 50;

    public static final int POWER_UP_SPEED_SCORE = 30;
    public static final int POWER_UP_JUMP_SCORE = 50;
    public static final int POWER_UP_DOUBLE_JUMP_SCORE = 100;
    public static final int POWER_UP_SPEED = 6;
    public static final int POWER_UP_JUMP_STRENGTH = -17;

    public static final int PENALTY_RANDOM_PLATFORMS = 200;
    public static final int PENALTY_RANDOM_PLATFORMS_INTERVAL = 10000;
}

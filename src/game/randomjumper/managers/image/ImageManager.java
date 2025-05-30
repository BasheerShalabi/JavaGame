package game.randomjumper.managers.image;

import game.randomjumper.config.GameConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ImageManager {

    private static final HashMap<String, BufferedImage> images = new HashMap<>();

    public static void preLoadImages() {
        preLoadImage("coin", "/images/nut.png" );
        preLoadImage("platform" , "/images/platform.png" );
        preLoadImage("ground", "/images/ground.png" );
        preLoadImage("background", "/images/background.png" );
        loadPlayerSprites();
        loadProjectileSprites();
        // add all other images here
    }

    public static void warmUpImage(BufferedImage img) {
        BufferedImage dummy = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = dummy.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
    }

    public static BufferedImage flipOverY(BufferedImage original){
        int w = original.getWidth();
        int h = original.getHeight();

        BufferedImage flipped = new BufferedImage(w, h, original.getType());
        Graphics2D g2d = flipped.createGraphics();

        AffineTransform at = new AffineTransform();
        at.scale(-1, 1);           // Flip horizontally
        at.translate(-w, 0);       // Shift back into visible area

        g2d.drawImage(original, at, null);
        g2d.dispose();

        return flipped;
    }


    public static void loadPlayerSprites() {
        String[] animations = { "idle", "run", "jump" , "hurt" };
        int[] frameCounts = {GameConfig.PLAYER_IDLE_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_RUN_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_JUMP_ANIMATION_FRAME_COUNT , GameConfig.PLAYER_HURT_ANIMATION_FRAME_COUNT };

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < frameCounts[i]; j++) {
                String key = "player/" + animations[i] + "-" + j;
                String path = "/images/player/" + "player-" + animations[i] + "-" + (j+1) + ".png";
                preLoadImage(key, path);
            }
        }
    }

    public static void loadProjectileSprites() {
        String[] animations = { "projectile" };
        int[] frameCounts = { GameConfig.PROJECTILE_ANIMATION_FRAME_COUNT };

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < frameCounts[i]; j++) {
                String key =  animations[i] + "-" + j;
                String path = "/images/projectile/" + animations[i] + "-" + (j+1) + ".png";
                preLoadImage(key, path);
            }
        }
    }


    private static void preLoadImage(String key, String path) {
        try {
            InputStream file = ImageManager.class.getResourceAsStream(path);
            assert file != null;
            BufferedImage img = ImageIO.read(file);
            warmUpImage(img);
            images.put(key, img);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Failed to load image: " + path);
        }
    }

    public static BufferedImage getImage(String key) {
        return images.get(key);
    }
}

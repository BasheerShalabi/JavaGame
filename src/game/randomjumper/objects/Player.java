package game.randomjumper.objects;

import game.randomjumper.config.GameConfig;
import game.randomjumper.managers.animation.Animatable;
import game.randomjumper.managers.image.ImageManager;
import game.randomjumper.managers.animation.SpriteAnimation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Animatable {
    private int jumpStrength = GameConfig.PLAYER_JUMP_STRENGTH;
    private int playerSpeed = GameConfig.PLAYER_SPEED;

    private final Rectangle playerRect;

    private int score = 0;
    private int playerHealth = GameConfig.PLAYER_HEALTH;
    private int playerX;
    private int playerY;
    private int direction = 1;

    public Player(int x, int y) {
        super("idle");
        this.playerX = x;
        this.playerY = y;
        this.playerRect = new Rectangle(x, y, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);

        animations.put("idle", SpriteAnimation.loadAnimation("idle", GameConfig.PLAYER_IDLE_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_ANIMATION_INTERVAL,"player/"));
        animations.put("run", SpriteAnimation.loadAnimation("run", GameConfig.PLAYER_RUN_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_ANIMATION_INTERVAL,"player/"));
        animations.put("jump", SpriteAnimation.loadAnimation("jump", GameConfig.PLAYER_JUMP_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_ANIMATION_INTERVAL,"player/"));
        animations.put("hurt", SpriteAnimation.loadAnimation("hurt", GameConfig.PLAYER_HURT_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_ANIMATION_INTERVAL,"player/"));
    }

    public void moveLeft() {
        this.playerX -= playerSpeed;
        this.playerRect.x = this.playerX;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void moveRight() {
        this.playerX += playerSpeed;
        this.playerRect.x = this.playerX;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
        this.playerRect.y = playerY;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
        this.playerRect.x = playerX;
    }

    public int getPlayerHeight() { return GameConfig.PLAYER_HEIGHT;}

    public int getPlayerWidth() {
        return GameConfig.PLAYER_WIDTH;
    }

    public Rectangle getPlayerRect() { return playerRect;}

    public void setPlayerScore(int score) { this.score = score;}

    public int getPlayerScore() { return score; }

    public void setJumpStrength(int jumpStrength) {
        this.jumpStrength = jumpStrength;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public int getjumpStrength() {
        return jumpStrength;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = Math.max(playerHealth, 0);
    }

    public void setAnimation(String key,int interval,int frameCount){
        animations.put(key, SpriteAnimation.loadAnimation(key, frameCount, interval , "player/"));
    }

    @Override
    public void draw(Graphics2D g) {
        BufferedImage frame = animations.get(currentState).getCurrentFrame();
        if(direction == 1){
            frame = ImageManager.flipOverY(frame);
        }
        g.drawImage(frame, playerRect.x-(GameConfig.PLAYER_WIDTH/2), playerRect.y-(GameConfig.PLAYER_HEIGHT/5), null);
    }
}

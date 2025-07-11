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
    private int playerShield = 0;
    private int direction = 1;
    private boolean hasShield = false;

    public Player(int x, int y) {
        super("idle");
        this.playerRect = new Rectangle(x, y, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);

        animations.put("idle", SpriteAnimation.loadAnimation("idle", GameConfig.PLAYER_IDLE_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_ANIMATION_INTERVAL,"player/"));
        animations.put("run", SpriteAnimation.loadAnimation("run", GameConfig.PLAYER_RUN_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_ANIMATION_INTERVAL,"player/"));
        animations.put("jump", SpriteAnimation.loadAnimation("jump", GameConfig.PLAYER_JUMP_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_ANIMATION_INTERVAL,"player/"));
        animations.put("hurt", SpriteAnimation.loadAnimation("hurt", GameConfig.PLAYER_HURT_ANIMATION_FRAME_COUNT, GameConfig.PLAYER_ANIMATION_INTERVAL,"player/"));
    }

    public void moveLeft() {
        this.playerRect.x -= playerSpeed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void moveRight() {
        this.playerRect.x += playerSpeed;
    }

    public int getPlayerX() {
        return playerRect.x;
    }

    public int getPlayerY() {
        return playerRect.y;
    }

    public void setPlayerY(int playerY) {
        this.playerRect.y = playerY;
    }

    public void setPlayerX(int playerX) {
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

    public int getPlayerShield(){
        return playerShield;
    }

    public boolean hasShield(){
        return hasShield;
    }

    public void setPlayerShield(int playerShield){
        hasShield = playerShield > 0;
        if(playerShield > 100){
            this.playerShield = 100;
        }else if(playerShield < 0) {
            playerHealth += playerShield;
            this.playerShield = 0;
        }else{
            this.playerShield = playerShield;
        }
    }

    public void setPlayerHealth(int playerHealth) {
        if(playerHealth > 100){
            this.playerHealth = 100;
        }else {
            this.playerHealth = Math.max(playerHealth, 0);
        }
    }

    public void setAnimation(String key,int interval,int frameCount){
        animations.put(key, SpriteAnimation.loadAnimation(key, frameCount, interval , "player/"));
    }

    @Override
    public void draw(Graphics2D g) {
        BufferedImage frame = animations.get(currentState).getCurrentFrame();
        if(direction == 1){
            frame = ImageManager.flipOverY(frame);
            g.drawImage(frame, playerRect.x-(int)(GameConfig.PLAYER_WIDTH/1.5), playerRect.y-(GameConfig.PLAYER_HEIGHT/5), null);
            return;
        }
        g.drawImage(frame, playerRect.x-(GameConfig.PLAYER_WIDTH/2), playerRect.y-(GameConfig.PLAYER_HEIGHT/5), null);
    }
}

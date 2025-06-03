package game.randomjumper.core;

import game.randomjumper.config.GameConfig;
import game.randomjumper.objects.Player;
import game.randomjumper.objects.Turret;
import game.randomjumper.managers.audio.SoundManager;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;

public class GameEngine {
    private final HashMap<String,Integer> powerUps = new HashMap<>();
    private final Player player;
    private final ArrayList<Rectangle> platforms;
    private final Ellipse2D.Double[] nuts;
    private final ArrayList<Turret> turrets;
    private final GameRenderer renderer;

    private int verticalVelocity = 0;
    private int powerUpOffset=0;

    private boolean isMoving=false;
    private boolean isJumping = false;
    private boolean isOnPlatform = false;
    private boolean isFalling = false;
    private boolean isDoubleJumping = false;
    private boolean canDoubleJump = false;
    private boolean gameOver = false;
    private boolean canRandomizePlatforms = false;
    private boolean isHit = false;
    private boolean hasSetAnimation = false;
    private boolean devMode = false;

    private long lastNutTime = System.currentTimeMillis();
    private long lastPlatformTime = System.currentTimeMillis();
    private int timer = 15;

    //Constructor
    public GameEngine(GameContext instance) {
        player = instance.getPlayer();
        platforms = instance.getPlatforms();
        nuts = instance.getNuts();
        turrets = instance.getTurrets();
        renderer = instance.getRenderer();
    }

    public void setDevMode(){
        devMode = !devMode;
    }

    public boolean isDevMode(){
        return devMode;
    }

    //Getters Setters
    public int getVerticalVelocity() {
        return verticalVelocity;
    }

    public boolean isHit() {
        return isHit;
    }
    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean isDoubleJumping() {
        return isDoubleJumping;
    }

    public void setMoving(boolean moving){
        isMoving=moving;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public HashMap<String,Integer> getPowerUps(){
        return powerUps;
    }


    //Update
    public void update() {
        applyPhysics();
        checkNutCollisions();
        spawnNuts();
        checkPowerUps();
        updateTurrets();
        CheckProjectileCollisions();
        randomizePlatforms();
        checkPlayerState();
        player.update();
        updateIsHit();

        if(player.getPlayerHealth()<=0){
            gameOver=true;
        }

    }

    // Returns true if the player has fallen below ground level
    public boolean isGameOver() {
        return gameOver;
    }

    // Handles player jumping logic, including double jump mechanics
    public void jump() {
        if (player.getPlayerY() >= GameConfig.GROUND_LEVEL - player.getPlayerHeight() || isOnPlatform) {
            verticalVelocity = player.getjumpStrength();
            isJumping = true;
            SoundManager.playClip("jump");
        } else if (canDoubleJump && !isDoubleJumping) {
            verticalVelocity = player.getjumpStrength();
            isDoubleJumping = true;
            SoundManager.playClip("doublejump");
        }
    }

    // Moves the player to the left
    public void moveLeft() {
        player.moveLeft();
    }

    // Moves the player to the right
    public void moveRight() {
        player.moveRight();
    }

    // Handles game physics including gravity, platform collision, and boundaries
    private void applyPhysics() {
        if (!isOnPlatform && player.getPlayerY() < GameConfig.GROUND_LEVEL - player.getPlayerHeight()) {
            if (verticalVelocity > GameConfig.MAX_DOWN_ACCELERATION) {
                verticalVelocity = GameConfig.MAX_DOWN_ACCELERATION;
            }
            verticalVelocity += GameConfig.GRAVITY;
        } else if (!isOnPlatform && !isJumping) {
            player.setPlayerY(GameConfig.GROUND_LEVEL - player.getPlayerHeight());
            verticalVelocity = 0;
        }

        for (Rectangle platform : platforms) {
            if ((!isJumping && (!isDoubleJumping || isFalling))
                    && doesTopIntersect(
                    player.getPlayerX(),
                    player.getPlayerY() + (player.getPlayerHeight() * 3 / 4),
                    player.getPlayerX() + player.getPlayerWidth(),
                    player.getPlayerY() + player.getPlayerHeight(),
                    platform.x, platform.y, platform.x + platform.width,
                    platform.y + platform.height)) {
                player.setPlayerY(platform.y - player.getPlayerHeight());
                verticalVelocity = 0;
                isOnPlatform = true;
                break;
            } else {
                isOnPlatform = false;
            }
        }

        if (verticalVelocity == 0) {
            isJumping = false;
        }

        if (isOnPlatform || player.getPlayerY() == GameConfig.GROUND_LEVEL - player.getPlayerHeight()) {
            isDoubleJumping = false;
        }

        isFalling = verticalVelocity > 0;
        player.setPlayerY(player.getPlayerY() + verticalVelocity);

        if (player.getPlayerX() < 0) {
            player.setPlayerX(0);
        } else if (player.getPlayerX() > 1000 - player.getPlayerWidth()) {
            player.setPlayerX(1000 - player.getPlayerWidth());
        }

        if (player.getPlayerY() > GameConfig.GROUND_LEVEL) {
            gameOver = true;
        }
    }

    private void updateIsHit(){
        if(timer>0 && isHit){
            timer--;
        }else{
            timer=20;
            setHit(false);
        }
    }

    private void checkPlayerState(){
        if(isHit){
            player.setState("hurt");
        }else
        if (isJumping || isDoubleJumping || isFalling) {
            player.setState("jump");
        } else if (isMoving) {
            player.setState("run");
        } else {
            player.setState("idle");
        }
    }

    // Checks for collisions between player and nuts, updates score accordingly
    private void checkNutCollisions() {
        for (int i = 0; i < nuts.length; i++) {
            if (nuts[i] == null) continue;
            if (player.getPlayerRect().intersects(nuts[i].x, nuts[i].y, nuts[i].width, nuts[i].height)) {
                player.setPlayerScore(player.getPlayerScore() + 1);
                nuts[i] = null;
                SoundManager.playClip("nut");
            }
        }
    }

    // Checks for collisions between player and projectiles, updates player health accordingly
    private void CheckProjectileCollisions(){
        for (Turret turret : turrets) {
            if (turret.getProjectile() == null) continue;
            if (player.getPlayerRect().intersects(turret.getProjectile().hitBox())) {
                this.isHit = true;
                player.setPlayerHealth(player.getPlayerHealth() - GameConfig.PROJECTILE_DAMAGE);
                turret.setNullProjectile();
                turret.setNextFireTime(System.currentTimeMillis()+(long)(Math.random()*GameConfig.PROJECTILE_FIRE_COOLDOWN_INTERVAL_MS/2)+GameConfig.PROJECTILE_FIRE_COOLDOWN_INTERVAL_MS/2);
                SoundManager.playClip("hit");
            }
        }
    }

    // Spawns new nuts on platforms at regular intervals
    private void spawnNuts() {
        boolean hasEmptySlot = false;
        for (Ellipse2D nut : nuts) {
            if (nut == null) {
                hasEmptySlot = true;
                break;
            }
        }

        if (!hasEmptySlot) {
            lastNutTime = System.currentTimeMillis();
            return;
        }

        int i = (int) (Math.random() * nuts.length);
        if (nuts[i] == null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastNutTime >= GameConfig.NUT_SPAWN_DELAY) {
                nuts[i] = new Ellipse2D.Double(platforms.get(i).x + (double) platforms.get(i).width / 2 - GameConfig.NUT_OFFSET_X,
                        platforms.get(i).y + (double) platforms.get(i).height / 2 - GameConfig.NUT_OFFSET_Y, GameConfig.NUT_SIZE, GameConfig.NUT_SIZE);
                lastNutTime = currentTime;
            }
        }
    }

    // Activates power-ups based on player's score
    private void checkPowerUps() {
        if (player.getPlayerScore() >= GameConfig.POWER_UP_SPEED_SCORE) {
            player.setPlayerSpeed(GameConfig.POWER_UP_SPEED);
            if (!hasSetAnimation) {
                hasSetAnimation = true;
                player.setAnimation("run", GameConfig.PLAYER_ANIMATION_INTERVAL_POWER_UP, GameConfig.PLAYER_RUN_ANIMATION_FRAME_COUNT);
            }
            setPowerUp("speed-boost");
        }
        if (player.getPlayerScore() >= GameConfig.POWER_UP_JUMP_SCORE) {
            player.setJumpStrength(GameConfig.POWER_UP_JUMP_STRENGTH);
            setPowerUp("jump-boost");
        }
        if (player.getPlayerScore() >= GameConfig.POWER_UP_DOUBLE_JUMP_SCORE) {
            canDoubleJump = true;
            setPowerUp("double-jump-boost");
        }
        if(player.getPlayerScore() >= GameConfig.PENALTY_RANDOM_PLATFORMS){
            canRandomizePlatforms=true;
        }
    }

    // Updates turrets, including their projectile positions and firing intervals
    private void updateTurrets(){
        for(Turret turret : turrets){
            turret.update(System.currentTimeMillis());
        }
    }

    // Randomizes platforms positions at regular intervals after a score milestone
    private void randomizePlatforms(){
        if(canRandomizePlatforms) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastPlatformTime >= GameConfig.PENALTY_RANDOM_PLATFORMS_INTERVAL) {
                platforms.clear();
                for (int y = GameConfig.PLATFORM_START_Y; y >= GameConfig.PLATFORM_END_Y; y -= GameConfig.PLATFORM_SPACING) {
                    platforms.add(new Rectangle((int) (Math.random() * GameConfig.PLATFORM_MAX_X) + GameConfig.PLATFORM_MIN_X, y, GameConfig.PLATFORM_WIDTH, GameConfig.PLATFORM_HEIGHT));
                }
                lastPlatformTime = currentTime;
                SoundManager.playClip("platform-swap");
            }
        }
    }

    public void setPowerUp(String key){
        if(!powerUps.containsKey(key)){
            powerUpOffset+=60;
            powerUps.put(key,powerUpOffset);
            SoundManager.playClip("powerup");
        }
    }

    //a method to check for intersection between 2 rectangles (not my doing, edit at your own risk!)
    public static boolean doesTopIntersect(
            int ax1, int ay1, int ax2, int ay2,
            int bx1, int by1, int bx2, int by2) {

        // Normalize coordinates
        int aLeft = Math.min(ax1, ax2);
        int aRight = Math.max(ax1, ax2);
        int aTop = Math.min(ay1, ay2);
        int aBottom = Math.max(ay1, ay2);

        int bLeft = Math.min(bx1, bx2);
        int bRight = Math.max(bx1, bx2);
        int bTop = Math.min(by1, by2);
        int bBottom = Math.max(by1, by2);

        // Check horizontal (x-axis) overlap
        boolean horizontalOverlap = !(aRight < bLeft || aLeft > bRight);

        // Check if the top of B intersects the bottom of A
        boolean verticalTouch = bTop <= aBottom && bTop >= aTop;

        return horizontalOverlap && verticalTouch;
    }
}

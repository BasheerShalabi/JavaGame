package gameObjects;

import main.GameConfig;

import java.awt.*;

public class Player {
    private int jumpStrength = -15;
    private int playerSpeed = 4;

    private final Rectangle playerRect;

    private int score = 0;
    private int playerHealth = 100;
    private int playerX;
    private int playerY;

    public Player(int x, int y) {
        this.playerX = x;
        this.playerY = y;
        this.playerRect = new Rectangle(x, y, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_HEIGHT);
    }

    public void moveLeft() {
        this.playerX -= playerSpeed;
        this.playerRect.x = this.playerX;
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


}

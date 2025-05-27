package gameObjects;

import main.GameConfig;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Projectile {
    private final Ellipse2D.Double projectile;
    private boolean active = true;
    private final int direction;

    public Projectile(int x, int y) {
        this.projectile = new Ellipse2D.Double(x, y, GameConfig.PROJECTILE_WIDTH, GameConfig.PROJECTILE_HEIGHT);
        if(x == 0){
            this.direction = 1;
        }else{
            this.direction = -1;
        }
    }

    public Rectangle hitBox(){
        return new Rectangle((int) projectile.x, (int) projectile.y, (int) projectile.width, (int) projectile.height);
    }

    public double getX(){
        return projectile.x;
    }

    public double getY(){
        return projectile.y;
    }

    public void update() {
        this.projectile.x += GameConfig.PROJECTILE_SPEED * this.direction;
        if (this.projectile.x < 0 || this.projectile.x > GameConfig.SCREEN_WIDTH) this.active = false;
    }

    public boolean isActive() {
        return this.active;
    }

    public int getDirection() {
        return direction;
    }
}

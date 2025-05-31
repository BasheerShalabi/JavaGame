package game.randomjumper.objects;

import game.randomjumper.config.GameConfig;
import game.randomjumper.managers.animation.Animatable;
import game.randomjumper.managers.animation.SpriteAnimation;
import game.randomjumper.managers.image.ImageManager;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Projectile extends Animatable {
    private final Ellipse2D.Double projectile;
    private boolean active = true;
    private final int direction;

    public Projectile(int x, int y) {
        super("projectile");
        this.projectile = new Ellipse2D.Double(x, y, GameConfig.PROJECTILE_WIDTH, GameConfig.PROJECTILE_HEIGHT);
        if(x == 0){
            this.direction = 1;
        }else{
            this.direction = -1;
        }

        animations.put("projectile", SpriteAnimation.loadAnimation("projectile",6,50,""));
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

    @Override
    public void update() {
        animations.get(currentState).update();
        this.projectile.x += GameConfig.PROJECTILE_SPEED * this.direction;
        if (this.projectile.x < 0 || this.projectile.x > GameConfig.SCREEN_WIDTH) this.active = false;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public void draw(Graphics2D g) {
        BufferedImage frame = animations.get(currentState).getCurrentFrame();
        if(direction == 1){
            frame = ImageManager.flipOverY(frame);
            g.drawImage(frame,(int)(projectile.x-(projectile.width)),(int)(projectile.y-(projectile.height/2)),GameConfig.PLAYER_WIDTH,GameConfig.PLAYER_HEIGHT/2,null);
            return;
        }
        g.drawImage(frame,(int)(projectile.x),(int)(projectile.y-(projectile.height/2)),GameConfig.PLAYER_WIDTH,GameConfig.PLAYER_HEIGHT/2,null);
    }
}

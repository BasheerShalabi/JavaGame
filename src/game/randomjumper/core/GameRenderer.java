package game.randomjumper.core;

import game.randomjumper.config.GameConfig;
import game.randomjumper.objects.Player;
import game.randomjumper.objects.Turret;
import game.randomjumper.managers.image.ImageManager;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GameRenderer {

    private final Player player;
    private final ArrayList<Rectangle> platforms;
    private final ArrayList<Turret> turrets;
    private final Ellipse2D.Double[] nuts;
    private final GameEngine engine;
    private boolean gameOver;


    public GameRenderer(GameContext instance){
        this.player = instance.getPlayer();
        this.platforms = instance.getPlatforms();
        this.turrets = instance.getTurrets();
        this.nuts = instance.getNuts();
        this.gameOver = instance.isGameOver();
        this.engine = instance.getEngine();

    }

    public void render(Graphics g) {
        // Cast to Graphics2D for better control
        Graphics2D g2d = (Graphics2D) g;

        // Draw background

        g2d.drawImage(ImageManager.getImage("background"),0, 0,null);

        // Draw ground

        g2d.drawImage(ImageManager.getImage("ground"),0, GameConfig.GROUND_LEVEL, GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT-GameConfig.GROUND_LEVEL,null);

        // Draw player
        player.draw(g2d);

        // Draw platforms
        for (Rectangle platform : platforms) {
            g2d.drawImage(ImageManager.getImage("platform"), platform.x, platform.y, platform.width, platform.height, null);
        }

        // Draw nuts
        for (Ellipse2D.Double nut : nuts) {
            if (nut != null) {
                g2d.drawImage(ImageManager.getImage("coin"),(int)(nut.x-(nut.width/3)),(int)(nut.y-(nut.height/3)),(int)(nut.width*1.5),(int)(nut.height*1.5),null);
            }
        }

        // Draw projectiles
        for(Turret turret : turrets) {
            if (turret.getProjectile() != null) {
                turret.getProjectile().draw(g2d);
            }
        }

        // Draw Power ups
        engine.getPowerUps().forEach((k,v) -> {
            g2d.drawImage(ImageManager.getImage(k),GameConfig.SCREEN_WIDTH-v,20,40,40,null);
        });

        g2d.setColor(new Color(0,0,0,.8f ));
        g2d.fillRoundRect(20,15,250,100,10,10);
        // Draw score
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("JoystixMonospace-Regular", Font.BOLD, 20));
        g2d.drawString("Score: " + player.getPlayerScore(), 50, 50);
        // Draw health
        g2d.setColor(Color.RED);
        g2d.drawString("Health: " + player.getPlayerHealth(), 50, 80);

        // Show the Game over message
        if (gameOver) {
            g2d.setColor(new Color(0,0,0,.8f ));
            g2d.fillRect((GameConfig.SCREEN_WIDTH/3)-10,(GameConfig.SCREEN_HEIGHT/3)-70,GameConfig.SCREEN_WIDTH/3,GameConfig.SCREEN_HEIGHT/3);
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("JoystixMonospace-Regular", Font.BOLD, 30));
            int width = g2d.getFontMetrics().stringWidth("GAME OVER");
            g2d.drawString("GAME OVER", (GameConfig.SCREEN_WIDTH / 2)-(width/2)-5, GameConfig.SCREEN_HEIGHT / 2 - 150);
        }

        // Dev mode variables
        if (engine.isDevMode()) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("X: "+player.getPlayerX(),50,GameConfig.SCREEN_HEIGHT-90);
            g.drawString("Y: "+player.getPlayerY(),130,GameConfig.SCREEN_HEIGHT-90);
            g.drawString("Jumping: "+engine.isJumping(),50,GameConfig.SCREEN_HEIGHT-140);
            g.drawString("Vertical Velocity: "+engine.getVerticalVelocity(),50,GameConfig.SCREEN_HEIGHT-210);
            g.drawString("Falling: "+engine.isFalling(),50,GameConfig.SCREEN_HEIGHT-280);
            g.drawString("DoubleJumping: "+engine.isDoubleJumping(),50,GameConfig.SCREEN_HEIGHT-350);

            g2d.setColor(Color.RED);

            g2d.drawRect(player.getPlayerX(),player.getPlayerY(),player.getPlayerWidth(), player.getPlayerHeight());

            // Draw platforms
            for (Rectangle platform : platforms) {
                g2d.drawRect( platform.x, platform.y, platform.width, platform.height);
            }

            // Draw nuts
            for (Ellipse2D.Double nut : nuts) {
                if (nut != null) {
                    g2d.drawRect((int)nut.x,(int)nut.y,(int)nut.width,(int)nut.height);
                }
            }

            // Draw projectiles
            for(Turret turret : turrets) {
                if (turret.getProjectile() != null) {
                    g2d.drawRect((int)turret.getProjectile().getX(),(int)turret.getProjectile().getY(),GameConfig.PROJECTILE_WIDTH,GameConfig.PROJECTILE_HEIGHT);
                }
            }
        }
    }
}
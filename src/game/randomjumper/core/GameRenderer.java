package game.randomjumper.core;

import game.randomjumper.config.GameConfig;
import game.randomjumper.objects.Player;
import game.randomjumper.objects.Turret;
import game.randomjumper.managers.image.ImageManager;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GameRenderer {

    public void render(Graphics g, GameEngine engine, Player player, ArrayList<Rectangle> platforms, Ellipse2D.Double[] coins, boolean devMode, boolean gameOver, int groundLevel, GamePanel panel) {
        // Cast to Graphics2D for better control
        Graphics2D g2d = (Graphics2D) g;

        // Draw background

        g2d.drawImage(ImageManager.getImage("background"),0, 0,null);

        // Draw ground

        g2d.drawImage(ImageManager.getImage("ground"),0, groundLevel, panel.getWidth(), panel.getHeight()-groundLevel,null);

        // Draw player
        player.draw(g2d);

        // Draw platforms
        for (Rectangle platform : platforms) {
            g2d.drawImage(ImageManager.getImage("platform"), platform.x, platform.y, platform.width, platform.height, null);
        }

        // Draw nuts
        for (Ellipse2D.Double coin : coins) {
            if (coin != null) {
                g2d.drawImage(ImageManager.getImage("coin"),(int)coin.x,(int)coin.y,(int)coin.width,(int)coin.height,null);
            }
        }

        // Draw projectiles
        for(Turret turret : engine.getTurrets()) {
            if (turret.getProjectile() != null) {
                turret.getProjectile().draw(g2d);
            }
        }

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
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("JoystixMonospace-Regular", Font.BOLD, 30));
            g2d.drawString("GAME OVER", GameConfig.SCREEN_WIDTH / 2 - 150, GameConfig.SCREEN_HEIGHT / 2);
        }

        // Dev mode variables
        if (devMode) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("X: "+player.getPlayerX(),50,panel.getHeight()-90);
            g.drawString("Y: "+player.getPlayerY(),130,panel.getHeight()-90);
            g.drawString("Jumping: "+engine.isJumping(),50,panel.getHeight()-140);
            g.drawString("Vertical Velocity: "+engine.getVerticalVelocity(),50,panel.getHeight()-210);
            g.drawString("Falling: "+engine.isFalling(),50,panel.getHeight()-280);
            g.drawString("DoubleJumping: "+engine.isDoubleJumping(),50,panel.getHeight()-350);
        }
    }
}
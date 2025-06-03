package game.randomjumper.core;

import game.randomjumper.objects.Player;
import game.randomjumper.objects.Turret;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public interface GameContext {
    Player getPlayer();
    GameEngine getEngine();
    GameRenderer getRenderer();
    GamePanel getPanel();
    ArrayList<Rectangle> getPlatforms();
    ArrayList<Turret> getTurrets();
    Ellipse2D.Double[] getNuts();
    boolean isGameOver();
}

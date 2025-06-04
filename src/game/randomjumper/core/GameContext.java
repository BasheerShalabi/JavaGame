package game.randomjumper.core;

import game.randomjumper.objects.Nut;
import game.randomjumper.objects.Player;
import game.randomjumper.objects.Turret;

import java.awt.*;
import java.util.ArrayList;

public interface GameContext {
    Player getPlayer();
    GameEngine getEngine();
    GameRenderer getRenderer();
    GamePanel getPanel();
    ArrayList<Rectangle> getPlatforms();
    ArrayList<Turret> getTurrets();
    Nut[] getNuts();
    boolean isGameOver();
}

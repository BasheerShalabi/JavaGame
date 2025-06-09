package game.randomjumper.objects;

import java.awt.geom.Ellipse2D;

public class ScoreNut extends PickUp {
    private int score = 1;

    public ScoreNut(boolean isGold, Ellipse2D.Double hitbox){
        super(hitbox,"nut");
        if(isGold) score = 5;
        if(isGold) super.imageKey = "goldnut";
    }

    public int getScore(){
        return score;
    }

}

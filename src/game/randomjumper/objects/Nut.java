package game.randomjumper.objects;

import java.awt.geom.Ellipse2D;

public class Nut {
    private final Ellipse2D.Double hitbox;
    private final boolean isGold;
    private int score = 1;
    private String imageKey = "nut";

    public Nut(boolean isGold,Ellipse2D.Double hitbox){
        this.hitbox = hitbox;
        this.isGold = isGold;
        if(isGold) score = 5;
        if(isGold) imageKey = "goldnut";
    }

    public boolean isGold(){
        return isGold;
    }

    public Ellipse2D.Double getHitbox(){
        return hitbox;
    }

    public int getScore(){
        return score;
    }

    public String getImageKey(){
        return imageKey;
    }
}

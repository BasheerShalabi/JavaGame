package game.randomjumper.objects;

import java.awt.geom.Ellipse2D;

public class HealthApple extends PickUp{

    private final int healthValue;

    public HealthApple(Ellipse2D.Double hitbox){
        super(hitbox,"apple");
        healthValue = (int)(Math.random()*5)+10;
    }

    public int getHealthValue(){
        return healthValue;
    }
}

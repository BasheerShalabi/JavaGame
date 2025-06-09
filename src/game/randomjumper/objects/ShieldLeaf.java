package game.randomjumper.objects;

import java.awt.geom.Ellipse2D;

public class ShieldLeaf extends PickUp{

    private final int shieldValue;

    public ShieldLeaf(Ellipse2D.Double hitbox){
        super(hitbox,"leaf");
        shieldValue = (int)(Math.random()*5)+5;
    }

    public int getShieldValue() {
        return shieldValue;
    }
}

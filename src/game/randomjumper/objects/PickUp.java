package game.randomjumper.objects;

import java.awt.geom.Ellipse2D;

public abstract class PickUp {
    protected final Ellipse2D.Double hitbox;
    protected String imageKey;

    protected PickUp(Ellipse2D.Double hitbox , String imageKey){
        this.hitbox = hitbox;
        this.imageKey = imageKey;
    }

    public Ellipse2D.Double getHitbox(){
        return hitbox;
    }

    public String getImageKey(){
        return imageKey;
    }
}

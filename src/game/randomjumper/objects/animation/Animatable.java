package game.randomjumper.objects.animation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Animatable {
     protected Map<String,SpriteAnimation> animations = new HashMap<>();
     protected String currentState;

     protected Animatable(String currentState){
         this.currentState = currentState;
     }
     public void update(double delta) {
         animations.get(currentState).update(delta);
     }

     public abstract void draw(Graphics2D g);

     public void setState(String state) {
         if (!state.equals(currentState)) {
                currentState = state;
                animations.get(currentState).reset();
         }
     }

}

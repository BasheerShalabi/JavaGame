package game.randomjumper.core;

import game.randomjumper.objects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GamePanel extends JPanel{
    private final GameRenderer renderer;
    private final GameEngine engine;
    private final Player player;

    private boolean gameOver;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    //Constructor
    public GamePanel(GameContext instance) {
        this.engine = instance.getEngine();
        this.renderer = instance.getRenderer();
        this.player = instance.getPlayer();
        this.gameOver = instance.isGameOver();
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        
        //Initialize a key listener for keyboard clicks
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }

            public void keyReleased(KeyEvent e) {
                handleKeyRelease(e);
            }
        });

    }


    //This method is important for instantaneous movement on button click
    //The Key listener has a delay for long key presses which results in clunky movement, this here solves it by using booleans as states
    protected void updateMovement(){
        if(engine.isPaused()){
            return;
        }
        if (leftPressed && rightPressed){
            engine.setMoving(false);
        }else
        if (leftPressed) {
            engine.moveLeft();
            engine.setMoving(true);
            player.setDirection(1);

        }else
        if (rightPressed) {
            engine.moveRight();
            engine.setMoving(true);
            player.setDirection(-1);
        }
    }

    //Method to handle key down events
    private void handleKeyPress(KeyEvent e){
        if(gameOver) return;
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_ESCAPE){
            engine.setIsPaused();
        }

        if(engine.isPaused()){
            return;
        }

        //Left and Right arrows
        if (key == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        //Space key for jumping and double jumping
        if (key == KeyEvent.VK_SPACE ){
            engine.jump();
        }

        //D key for Dev Mode toggle
        if(key==KeyEvent.VK_D){
            engine.setDevMode();
        }

    }

    //Method to handle key up events, mostly for smooth movement
    private void handleKeyRelease(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            leftPressed = false;
            engine.setMoving(false);
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
            engine.setMoving(false);
        }
    }

    //This method draws everything on the panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Render graphics
        renderer.render(g);
    }


}

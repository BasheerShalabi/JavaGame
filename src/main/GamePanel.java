package main;

import gameObjects.Player;
import gameObjects.Turret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    //Initialize Objects
    private Thread gameThread;
    private Player player;
    private ArrayList<Rectangle> platforms = new ArrayList<>();
    private Ellipse2D.Double[] coins;
    private GameEngine engine;
    private GameRenderer renderer;
    private ArrayList<Turret> turrets = new ArrayList<>();
    
    //Initialize Toggles
    private boolean devMode = false;
    private boolean gameOver = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    //Constructor
    public GamePanel() {
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
        
        startGame();
    }

    //Initialize Game Objects and start a thread
    private void startGame(){

        //Initialize thread and player objects
        gameThread = new Thread(this);
        player = new Player(GameConfig.INITIAL_PLAYER_X, GameConfig.INITIAL_PLAYER_Y);

        //Initialize platforms with random horizontal values
        for (int y = GameConfig.PLATFORM_START_Y; y >= GameConfig.PLATFORM_END_Y; y -= GameConfig.PLATFORM_SPACING) {
            platforms.add(new Rectangle((int) (Math.random() * GameConfig.PLATFORM_MAX_X) + GameConfig.PLATFORM_MIN_X, y, GameConfig.PLATFORM_WIDTH, GameConfig.PLATFORM_HEIGHT));
        }

        //Initialize turrets with random vertical values
        for (Rectangle platform : platforms) {
            turrets.add(new Turret((int) (Math.random() * 100)>=50 ? 0 : GameConfig.SCREEN_WIDTH, platform.y));
        }

        //Initialize coin array
        coins = new Ellipse2D.Double[platforms.size()];

        //Initialize the game engine
        engine = new GameEngine(player, platforms, coins, turrets);

        //Initialize renderer
        renderer = new GameRenderer();

        SoundManager.preloadSound(GameConfig.COIN_COLLECT_SOUND);
        SoundManager.preloadSound(GameConfig.PLAYER_HIT_SOUND);
        SoundManager.preloadSound(GameConfig.PLAYER_JUMP_SOUND);
        SoundManager.preloadSound(GameConfig.PLAYER_DOUBLE_JUMP_SOUND);
        SoundManager.preloadSound(GameConfig.PLATFORM_SWAP_SOUND);

        //Start thread
        gameThread.start();
    }

    //This method executes when the thread starts, this is called the Game Loop
    @Override
    public void run(){
        // Wait for the components to Initialize before starting the game
        while (getWidth() == 0 || getHeight() == 0 || !isVisible()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        //The infinite game loop! (until you lose ofc)
        //Updates 60 times a second
        while(!gameOver){
            update();
            repaint();
            try{
                Thread.sleep(GameConfig.FRAME_TIME);
            }catch(InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }

    //a method to check for intersection between 2 rectangles (not my doing, edit at your own risk!)
    public static boolean doesTopIntersect(
            int ax1, int ay1, int ax2, int ay2,
            int bx1, int by1, int bx2, int by2) {

        // Normalize coordinates
        int aLeft = Math.min(ax1, ax2);
        int aRight = Math.max(ax1, ax2);
        int aTop = Math.min(ay1, ay2);
        int aBottom = Math.max(ay1, ay2);

        int bLeft = Math.min(bx1, bx2);
        int bRight = Math.max(bx1, bx2);
        int bTop = Math.min(by1, by2);
        int bBottom = Math.max(by1, by2);

        // Check horizontal (x-axis) overlap
        boolean horizontalOverlap = !(aRight < bLeft || aLeft > bRight);

        // Check if the top of B intersects the bottom of A
        boolean verticalTouch = bTop <= aBottom && bTop >= aTop;

        return horizontalOverlap && verticalTouch;
    }

    //This method is important for instantaneous movement on button click
    //The Key listener has a delay for long key presses which results in clunky movement, this here solves it by using booleans as states
    private void updateMovement(){
        if (leftPressed) {
            engine.moveLeft();
        }
        if (rightPressed) {
            engine.moveRight();
        }
    }

    //The update method gets called inside the game loop
    private void update(){
        updateMovement();
        engine.update();
        gameOver=engine.isGameOver();
    }

    //Method to handle key down events
    private void handleKeyPress(KeyEvent e){
        int key = e.getKeyCode();

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
            devMode=!devMode;
        }

    }

    //Method to handle key up events, mostly for smooth movement
    private void handleKeyRelease(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
    }

    //This method draws everything on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Render graphics
        renderer.render(g, engine, engine.getPlayer(), platforms, coins, devMode, gameOver, GameConfig.GROUND_LEVEL, this);
    }

}

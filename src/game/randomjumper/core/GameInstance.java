package game.randomjumper.core;

import game.randomjumper.config.GameConfig;
import game.randomjumper.managers.audio.SoundManager;
import game.randomjumper.managers.ui.MenuManager;
import game.randomjumper.objects.pickups.PickUp;
import game.randomjumper.objects.Player;
import game.randomjumper.objects.Turret;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameInstance extends Thread implements Runnable , GameContext{
    protected Player player;
    protected final ArrayList<Rectangle> platforms = new ArrayList<>();
    protected PickUp[] pickUps;
    protected GameEngine engine;
    protected GameRenderer renderer;
    protected GamePanel panel;
    protected final ArrayList<Turret> turrets = new ArrayList<>();

    //Initialize Toggles
    protected boolean gameOver = false;
    //Constructor
    public GameInstance() {
        startGame();
    }


    //Initialize Game Objects and start a thread
    private void startGame(){
        //Initialize player
        player = new Player(GameConfig.INITIAL_PLAYER_X, GameConfig.INITIAL_PLAYER_Y);

        //Initialize platforms with random horizontal values
        for (int y = GameConfig.PLATFORM_START_Y; y >= GameConfig.PLATFORM_END_Y; y -= GameConfig.PLATFORM_SPACING) {
            platforms.add(new Rectangle((int) (Math.random() * GameConfig.PLATFORM_MAX_X) + GameConfig.PLATFORM_MIN_X, y, GameConfig.PLATFORM_WIDTH, GameConfig.PLATFORM_HEIGHT));
        }

        //Initialize turrets with random vertical values
        for (Rectangle platform : platforms) {
            turrets.add(new Turret((int) (Math.random() * 100)>=50 ? 0 : GameConfig.SCREEN_WIDTH, platform.y));
        }

        //Initialize nut array
        pickUps = new PickUp[platforms.size()];

        //Initialize the game engine
        engine = new GameEngine(this);

        //Initialize renderer
        renderer = new GameRenderer(this);

        //Initialize panel
        panel = new GamePanel(this);

        //Start thread
        this.start();
    }

    public void stopGame() {
        if (this.isAlive()) {
            this.interrupt();
        }
    }

    //This method executes when the thread starts, this is called the Game Loop
    @Override
    public void run(){
        SoundManager.playMusic();

        final long optimalTime = 1_000_000_000 / GameConfig.TARGET_FPS; // 1 second in nanoseconds

        long lastLoopTime = System.nanoTime();

        //The infinite game loop! (until you lose ofc)
        //Updates 60 times a second
        while(!gameOver && !Thread.currentThread().isInterrupted()){

            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;

            double delta = updateLength / (double) optimalTime;

            update(delta);
            panel.repaint();

            long sleepTime = (lastLoopTime + optimalTime - System.nanoTime()) / 1_000_000;
            if(sleepTime > 0){
                try{
                    Thread.sleep(sleepTime);
                }catch(InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
        SoundManager.stopMusic();
        SoundManager.playClip("gameover");
        SwingUtilities.invokeLater(() -> {
            MenuManager.tryAgainMenu(panel);
        });
    }

    //The update method gets called inside the game loop
    private void update(double delta){
        panel.updateMovement(delta);
        engine.update(delta);
        gameOver=engine.isGameOver();
    }

    @Override
    public Player getPlayer(){
        return player;
    }
    @Override
    public GameEngine getEngine(){
        return engine;
    }
    @Override
    public GamePanel getPanel(){
        return panel;
    }
    @Override
    public GameRenderer getRenderer(){
        return renderer;
    }
    @Override
    public ArrayList<Rectangle> getPlatforms(){
        return platforms;
    }
    @Override
    public ArrayList<Turret> getTurrets(){
        return turrets;
    }
    @Override
    public PickUp[] getPickUps(){
        return pickUps;
    }
    @Override
    public boolean isGameOver(){
        return gameOver;
    }


}

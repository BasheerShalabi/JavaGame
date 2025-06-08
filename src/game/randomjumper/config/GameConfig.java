package game.randomjumper.config;

//this class simply contains all the constants used in the game
public class GameConfig {

    private GameConfig() {}

    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 800;
    public static final int FRAME_TIME = 1000/60;

    public static final int PROJECTILE_MIN_FIRE_INTERVAL_MS = 1000;
    public static final int PROJECTILE_MAX_FIRE_INTERVAL_MS = 5000;
    public static final int PROJECTILE_FIRE_COOLDOWN_INTERVAL_MS = 4000;
    public static final int PROJECTILE_ANIMATION_FRAME_COUNT = 6;
    public static final int PROJECTILE_DAMAGE = 15;
    public static final int PROJECTILE_SPEED = 3;
    public static final int PROJECTILE_WIDTH = 20;
    public static final int PROJECTILE_HEIGHT = 10;

    public static final int PLATFORM_SPACING = 70;
    public static final int PLATFORM_START_Y = 620;
    public static final int PLATFORM_END_Y = 200;
    public static final int PLATFORM_MIN_X = 100;
    public static final int PLATFORM_MAX_X = 550;
    public static final int PLATFORM_WIDTH = 200;
    public static final int PLATFORM_HEIGHT = 20;

    public static final int PLAYER_HEIGHT = 40;
    public static final int PLAYER_WIDTH = 40;
    public static final int PLAYER_HEALTH = 100;
    public static final int PLAYER_SPEED = 4;
    public static final int PLAYER_JUMP_STRENGTH = -15;
    public static final int PLAYER_ANIMATION_INTERVAL = 100;
    public static final int PLAYER_ANIMATION_INTERVAL_POWER_UP = 50;
    public static final int PLAYER_RUN_ANIMATION_FRAME_COUNT = 6;
    public static final int PLAYER_IDLE_ANIMATION_FRAME_COUNT = 8;
    public static final int PLAYER_JUMP_ANIMATION_FRAME_COUNT = 4;
    public static final int PLAYER_HURT_ANIMATION_FRAME_COUNT = 2;

    public static final int INITIAL_PLAYER_X = 500;
    public static final int INITIAL_PLAYER_Y = 200;

    public static final int GROUND_OFFSET = 100;
    public static final int GRAVITY = 1;
    public static final int MAX_DOWN_ACCELERATION = 10;
    public static final int GROUND_LEVEL = SCREEN_HEIGHT - GROUND_OFFSET;

    public static final int NUT_SPAWN_DELAY = 1500;
    public static final int NUT_SIZE = 20;
    public static final int NUT_OFFSET_X = 10;
    public static final int NUT_OFFSET_Y = 50;

    public static final int POWER_UP_SPEED_SCORE = 30;
    public static final int POWER_UP_JUMP_SCORE = 50;
    public static final int POWER_UP_DOUBLE_JUMP_SCORE = 100;
    public static final int POWER_UP_SPEED = 6;
    public static final int POWER_UP_JUMP_STRENGTH = -17;
    public static final int POWER_UP_MESSAGE_INTERVAL = 2000;

    public static final int PENALTY_RANDOM_PLATFORMS = 200;
    public static final int PENALTY_RANDOM_PLATFORMS_INTERVAL = 10000;
}

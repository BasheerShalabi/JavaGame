package game.randomjumper.objects;

import game.randomjumper.config.GameConfig;

import java.util.Random;

public class Turret {
    private final int x, y;
    private Projectile currentProjectile = null;
    private long nextFireTime = 0;

    private final Random random = new Random();

    public Turret(int x, int y) {
        this.x = x;
        this.y = y;
        scheduleNextFire();
    }

    public Projectile getProjectile() {
        return currentProjectile;
    }

    public void setNullProjectile() {
        this.currentProjectile = null;
    }

    public void setNextFireTime(long nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public void update(long currentTimeMillis) {

        // Remove the projectile if it is no longer active and schedule the next one
        if (currentProjectile != null && !currentProjectile.isActive()) {
            currentProjectile = null;
            scheduleNextFire();
        }

        // Try to fire if no active projectile and cooldown passed
        if (currentProjectile == null && currentTimeMillis >= nextFireTime) {
            fire();
        }

        // Update projectile if alive
        if (currentProjectile != null) {
            currentProjectile.update();
        }
    }

    private void fire() {
        currentProjectile = new Projectile(x, y-(GameConfig.NUT_OFFSET_Y /2));
    }

    private void scheduleNextFire() {
        int interval = GameConfig.PROJECTILE_MIN_FIRE_INTERVAL_MS + random.nextInt(GameConfig.PROJECTILE_MAX_FIRE_INTERVAL_MS - GameConfig.PROJECTILE_MIN_FIRE_INTERVAL_MS);
        nextFireTime = System.currentTimeMillis() + interval;
    }
}

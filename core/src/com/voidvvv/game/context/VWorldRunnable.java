package com.voidvvv.game.context;

import com.badlogic.gdx.Gdx;

public class VWorldRunnable implements Runnable {
    private long lastTime = -1;

    private float deltaTime;

    public static final float timeFactor = 1000000000.0f;
    @Override
    public void run() {
        while (true){
            long time = System.nanoTime();
            if (lastTime == -1) {
                lastTime = time;
            }
            deltaTime = (time - lastTime) / timeFactor;
            WorldContext.getWorld().getBox2dWorld().step(deltaTime,10,10);
            lastTime = time;
        }
    }
}

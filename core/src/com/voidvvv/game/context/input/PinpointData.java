package com.voidvvv.game.context.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class PinpointData {    public Vector2 position = new Vector2();
    public boolean running = false;
    public float time;
    public float maxTime = 2f;
    public float size = 5f;

    public void begin (float x, float y) {
        running = true;
        time = maxTime;
        position.set(x, y);
    }
}

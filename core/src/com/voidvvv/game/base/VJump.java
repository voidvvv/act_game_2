package com.voidvvv.game.base;

import com.badlogic.gdx.math.Vector3;
import com.voidvvv.game.context.world.WorldContext;

public class VJump {
    public float time;

    public Vector3 vel = new Vector3();

    public void update (float delta) {
        this.vel.add(0,0, WorldContext.GRAVITY_CONST * delta);
    }


    public void reset () {
        this.vel.set(0,0,0);
    }

    public void jump (float v) {
        this.vel.z = v;
    }
}

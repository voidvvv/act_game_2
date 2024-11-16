package com.voidvvv.game.context;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class WorldContext {
    public static float GRAVITY_CONST = -9.8F;

    public static final short ROLE = 1;

    public static final short OBSTACLE = 1<<1;

    public static final BodyDef.BodyType defaultBodyType = BodyDef.BodyType.DynamicBody;

    private  VWorld world;

    public  VWorld currentWorld() {
        return world;
    }

    public void init() {
        if (world == null) {
            world = new VWorld();
        }
        world.init();
    }
}
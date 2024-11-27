package com.voidvvv.game.screen.test.ui;

import com.badlogic.gdx.math.Vector2;

public class Box2dUnitConverter {
    public static final float BOX2D_UNIT = 20F;

    public static float worldToBox2d(float i) {
        return i / BOX2D_UNIT;
    }

    public static float box2dToWorld(float i) {
        return i * BOX2D_UNIT;
    }

    public static Vector2 worldToBox2d(Vector2 v2) {
        return v2.scl(1f/BOX2D_UNIT);
    }

    public static Vector2 box2dToWorld(Vector2 v2) {
        return v2.scl(BOX2D_UNIT);
    }
}

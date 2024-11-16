package com.voidvvv.game.base;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.context.VWorld;

public class PathDelegateActor extends VCharacter implements Pool.Poolable {
    private VCharacter character;

    private Vector2 lastPosition;

    int pathId = 0;
    boolean pathing = false;

    public PathDelegateActor(VCharacter character, VWorld world) {
        this.character = character;
        this.setWorld(world);


    }


    @Override
    public void reset() {

    }
}

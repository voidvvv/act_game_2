package com.voidvvv.game.v2;

import com.voidvvv.game.v2.world.VWorld;

public interface VActorRender {
    public abstract void init();

    public abstract void render(VActor actor, float delta);

    public abstract void dispose();
}

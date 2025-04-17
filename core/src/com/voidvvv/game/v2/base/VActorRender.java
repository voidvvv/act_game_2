package com.voidvvv.game.v2.base;

public interface VActorRender {
    public abstract void init();

    public abstract void render(VActor actor, float delta);

    public abstract void dispose();
}

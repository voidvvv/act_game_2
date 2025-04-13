package com.voidvvv.game.v2.world;

public abstract class VRenderdWorld implements VWorld {
    protected WorldRender worldRender;

    public WorldRender getWorldRender() {
        return worldRender;
    }

    public void setWorldRender(WorldRender worldRender) {
        this.worldRender = worldRender;
    }

    @Override
    public void draw() {
        if (worldRender != null) {
            worldRender.render(this, 0);
        }
    }
}

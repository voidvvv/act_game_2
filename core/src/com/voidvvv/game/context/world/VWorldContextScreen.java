package com.voidvvv.game.context.world;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;

public class VWorldContextScreen extends ScreenAdapter {
    protected WorldContext worldContext;

    public WorldContext getWorldContext() {
        return worldContext;
    }

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }
}

package com.voidvvv.game.plugin;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.Stoppable;

public interface Plugin extends Pool.Poolable, Stoppable {

    int version();
    void start();
    public void update(float delta);
}

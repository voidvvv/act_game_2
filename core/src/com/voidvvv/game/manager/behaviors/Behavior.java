package com.voidvvv.game.manager.behaviors;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.VActor;

public interface Behavior extends Pool.Poolable {
    public VActor owner();
    public void does();
    public int behaviorType();
    public void attach(VActor actor);
}

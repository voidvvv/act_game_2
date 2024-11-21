package com.voidvvv.game.manager.behaviors;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.context.VWorld;

public interface Behavior extends Pool.Poolable {
    public VWorld world();
    void setOwner(VActor owner);
    public VActor owner();
    public void does();
    public int behaviorType();
    public void attach(VActor actor);
}

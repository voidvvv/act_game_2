package com.voidvvv.game.manager.behaviors;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.context.world.VActWorld;
import com.voidvvv.game.manager.event.WorldEvent;

public interface Behavior extends Pool.Poolable {
    public VActWorld world();
    void setOwner(VActor owner);
    public VActor owner();
    public void does();
    public int behaviorType();
    public void attach(VActor actor);

    public boolean didFlag();

    /**
     * the event trigger this behavior
     * @return
     */
    WorldEvent event();
}

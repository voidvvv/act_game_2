package com.voidvvv.game.v2.base;

import com.voidvvv.game.v2.base.world.VWorldActor;

public interface VComponent {
    void init();

    void update(float delta);

    void dispose();


    void resetActor(VWorldActor actor);
}

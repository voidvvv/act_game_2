package com.voidvvv.game.controller;

import com.badlogic.gdx.ai.msg.Telegraph;
import com.voidvvv.game.base.VActor;

public interface TargetController extends Telegraph {
    void update();

    VActor getActor();

    void setActor();
}

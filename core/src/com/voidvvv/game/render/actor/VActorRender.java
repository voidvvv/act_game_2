package com.voidvvv.game.render.actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.voidvvv.game.base.VActor;

public interface VActorRender <T extends VActor> {
    public void init();

    void render(T actor, Batch batch , float parentAlpha);
}

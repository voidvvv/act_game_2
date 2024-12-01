package com.voidvvv.game.render.actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.voidvvv.game.base.VActor;

public interface VActorRender <T> {
    public void init();

    void render(T actor, Batch batch , float parentAlpha);

}

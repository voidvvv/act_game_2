package com.voidvvv.game.render.actor.test.bullet;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.test.TestBullet;
import com.voidvvv.game.render.actor.VActorRender;

public class TestBulletRender implements VActorRender<TestBullet> {
    Texture base;
    @Override
    public void init() {

        base = ActGame.gameInstance().getAssetManager().get("badlogic.jpg", Texture.class, false);
        if (base == null) {
            ActGame.gameInstance().getAssetManager().load("badlogic.jpg", Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            base = ActGame.gameInstance().getAssetManager().get("badlogic.jpg", Texture.class);

        }
    }

    @Override
    public void render(TestBullet actor, Batch batch, float parentAlpha) {
        float liveTime = actor.liveTime;
        batch.draw(base, actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
    }
}

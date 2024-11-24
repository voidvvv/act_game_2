package com.voidvvv.game.render.actor.test.bob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.BobAssetConstant;
import com.voidvvv.game.base.test.Bob;
import com.voidvvv.game.render.actor.VActorRender;

public class DefaultBobRender implements VActorRender<Bob> {
    private BobAssetConstant bobAssetConstant;

    public DefaultBobRender() {

        bobAssetConstant = new BobAssetConstant();
        bobAssetConstant.init();
    }

    @Override
    public void init() {

    }

    @Override
    public void render(Bob actor, Batch batch, float parentAlpha) {
        TextureRegion texture = bobAssetConstant.currentAnim(actor);
        if (texture == null) {
            return;
        }

        float x = actor.position.x;
        float y = actor.position.y;
        int regionWidth = texture.getRegionWidth() * 1;
        int regionHeight = texture.getRegionHeight() * 1;
        batch.draw(texture, x - regionWidth/2, y + actor.position.z, regionWidth, regionHeight);
    }
}

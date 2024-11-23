package com.voidvvv.game.render.actor.bob;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    public void render(Bob actor, Batch batch, float parentAlpha) {
        TextureRegion texture = bobAssetConstant.currentAnim(actor.getSelfStatusStateMachine().getCurrentState());
        batch.draw(texture, actor.getX(), actor.getY(), actor.getWidth() * 1.5f, actor.getHeight() *1.5f);

    }
}

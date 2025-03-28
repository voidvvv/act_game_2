package com.voidvvv.game.render.actor.slime;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;
import com.voidvvv.game.base.actors.slime.Slime;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.base.state.normal.DyingStatus;
import com.voidvvv.game.base.state.normal.Idle;
import com.voidvvv.game.base.state.normal.Spelling;
import com.voidvvv.game.base.state.normal.Walking;
import com.voidvvv.game.render.actor.VActorRender;
import com.voidvvv.game.utils.ReflectUtil;

public class SlimeSimpleRender implements VActorRender<Slime> {
    Texture baseTexture = null;
    Animation<TextureRegion> attack = null;
    Animation<TextureRegion> idle = null;
    Animation<TextureRegion> walk = null;
    Animation<TextureRegion> dying = null;

    @Override
    public void init() {
        // init attack
        Texture texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IMAGE, Texture.class, false);
        if (texture == null) {
            ActGame.gameInstance().getAssetManager().load(AssetConstant.SLIME_IMAGE, Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IMAGE, Texture.class);
        }
        baseTexture = texture;
        TextureRegion[][] tr = TextureRegion.split(baseTexture, 64, 64);
        attack = AssetConstant.makeCommonAnimation(tr[0]);
        // idle
        texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IDLE_IMAGE, Texture.class, false);
        if (texture == null) {
            ActGame.gameInstance().getAssetManager().load(AssetConstant.SLIME_IDLE_IMAGE, Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IDLE_IMAGE, Texture.class);
        }
        tr = TextureRegion.split(texture, 64, 64);
        idle = AssetConstant.makeCommonAnimation(tr[0]);

        texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_WALK_IMAGE, Texture.class, false);
        if (texture == null) {
            ActGame.gameInstance().getAssetManager().load(AssetConstant.SLIME_WALK_IMAGE, Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_WALK_IMAGE, Texture.class);
        }
        tr = TextureRegion.split(texture, 64, 64);
        walk = AssetConstant.makeCommonAnimation(tr[0]);

        texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_DYING_IMAGE, Texture.class, false);
        if (texture == null) {
            ActGame.gameInstance().getAssetManager().load(AssetConstant.SLIME_DYING_IMAGE, Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_DYING_IMAGE, Texture.class);
        }
        tr = TextureRegion.split(texture, 64, 64);
        dying = AssetConstant.makeCommonAnimation(tr[0]);

    }

    @Override
    public void render(Slime actor, Batch batch, float parentAlpha) {
        VCharactorStatus state = actor.getStateMachine().getCurrentState();
        if (ReflectUtil.cast(state, Idle.class) != null) {
            TextureRegion keyFrame = idle.getKeyFrame(actor.statusProgress, true);
            batch.draw(keyFrame, actor.position.x - keyFrame.getRegionWidth() / 2f, actor.position.y - 16f - 8f);
        } else if (ReflectUtil.cast(state, Walking.class) != null) {
            TextureRegion keyFrame = walk.getKeyFrame(actor.statusProgress, true);
            batch.draw(keyFrame, actor.position.x - keyFrame.getRegionWidth() / 2f, actor.position.y - 16f - 8f);
        } else if (ReflectUtil.cast(state, Spelling.class) != null) {
            TextureRegion keyFrame = attack.getKeyFrame(actor.statusProgress, true);
            batch.draw(keyFrame, actor.position.x - keyFrame.getRegionWidth() / 2f, actor.position.y - 16f - 8f);
        } else if (ReflectUtil.cast(state, DyingStatus.class) != null) {
            TextureRegion keyFrame = dying.getKeyFrame(actor.statusProgress, true);
            batch.draw(keyFrame, actor.position.x - keyFrame.getRegionWidth() / 2f, actor.position.y - 16f - 8f);
        } else {
            TextureRegion keyFrame = attack.getKeyFrame(actor.statusProgress, true);
            batch.draw(keyFrame, actor.position.x - keyFrame.getRegionWidth() / 2f, actor.position.y - 16f - 8f);

        }

    }
}

package com.voidvvv.game.render.actor.slime;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.actors.Slime;
import com.voidvvv.game.render.actor.VActorRender;

public class SlimeSimpleRender implements VActorRender<Slime> {
    Texture baseTexture = null;
    Animation<TextureRegion> attack = null;
    Animation<TextureRegion> idle = null;
    Animation<TextureRegion> walk = null;

    @Override
    public void init() {
        // init attack
        Texture texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IMAGE, Texture.class, false);
        if (texture == null) {
            ActGame.gameInstance().getAssetManager().load(AssetConstant.SLIME_IMAGE,Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IMAGE, Texture.class);
        }
        baseTexture = texture;
        TextureRegion[][] tr = TextureRegion.split(baseTexture, 64,64);
        attack = AssetConstant.makeCommonAnimation(tr[0]);
        // idle
        texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IDLE_IMAGE, Texture.class, false);
        if (texture == null) {
            ActGame.gameInstance().getAssetManager().load(AssetConstant.SLIME_IDLE_IMAGE,Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IDLE_IMAGE, Texture.class);
        }
        tr = TextureRegion.split(texture, 64,64);
        idle = AssetConstant.makeCommonAnimation(tr[0]);

        texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_WALK_IMAGE, Texture.class, false);
        if (texture == null) {
            ActGame.gameInstance().getAssetManager().load(AssetConstant.SLIME_WALK_IMAGE,Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_WALK_IMAGE, Texture.class);
        }
        tr = TextureRegion.split(texture, 64,64);
        walk = AssetConstant.makeCommonAnimation(tr[0]);

    }

    @Override
    public void render(Slime actor, Batch batch, float parentAlpha) {
        TextureRegion keyFrame = walk.getKeyFrame(actor.statusProgress, true);
        batch.draw(keyFrame, actor.position.x - keyFrame.getRegionWidth()/2f, actor.position.y - 16f - 8f);
        batch.end();
        ShapeRenderer shapeRenderer = ActGame.gameInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(actor.getX(), actor.getY(), actor.physicAttr.box2dHx *2 , actor.physicAttr.box2dHy *2 );
//        shapeRenderer.setColor(Color.BLUE);
//
//        shapeRenderer.rect(actor.getX(), actor.getY() - actor.physicAttr.box2dHy, actor.getWidth(), actor.getHeight());

        shapeRenderer.end();
        batch.begin();
    }
}

package com.voidvvv.game.render.actor.test.stone;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;
import com.voidvvv.game.base.test.VObstacle;
import com.voidvvv.game.render.actor.VActorRender;

public class StoneRender implements VActorRender<VObstacle> {
    Texture stone;
    public void init() {
        if (stone == null) {
            stone = ActGame.gameInstance().getAssetManager().get(AssetConstant.STONE_MAN, Texture.class,false);
            if (stone == null) {
                ActGame.gameInstance().getAssetManager().load(AssetConstant.STONE_MAN, Texture.class);
                ActGame.gameInstance().getAssetManager().finishLoading();
                stone = ActGame.gameInstance().getAssetManager().get(AssetConstant.STONE_MAN, Texture.class);
            }
        }

    }

    @Override
    public void render(VObstacle actor, Batch batch, float parentAlpha) {
        batch.draw(stone, actor.getX(), actor.getY()- actor.physicAttr.box2dHy,actor.getWidth(), actor.getHeight());
        batch.end();
        ShapeRenderer shapeRenderer = ActGame.gameInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(actor.getX(), actor.getY(), actor.physicAttr.box2dHx *2 , actor.physicAttr.box2dHy *2 );
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());

        shapeRenderer.end();
        batch.begin();
    }
}

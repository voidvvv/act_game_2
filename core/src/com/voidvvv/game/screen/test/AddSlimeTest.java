package com.voidvvv.game.screen.test;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;
import com.voidvvv.game.context.VWorld;

public class AddSlimeTest extends Actor implements Telegraph {
    public static final int PRE_ADD_SLIME = 0xFFFF-1;
    boolean sign = false;
    private TextureRegion slimeTexture;
    private float x;
    private float y;
    public VWorld world;
    public void init () {
        MessageManager.getInstance().addListener(this, PRE_ADD_SLIME);
        getStage().addListener(new InputListener(){
            @Override
            public boolean mouseMoved(InputEvent event, float x, float y) {
                if (!sign) {
                    return false;
                }
                float stageX = event.getStageX();
                float stageY = event.getStageY();
                AddSlimeTest.this.x = stageX;
                AddSlimeTest.this.y = stageY;
                return true;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!sign) {
                    return false;
                }
                sign = false;
                float stageX = event.getStageX();
                float stageY = event.getStageY();
                AddSlimeTest.this.x = stageX;
                AddSlimeTest.this.y = stageY;
                MessageManager.getInstance().dispatchMessage(TestScreen.ADD_SLIME);
                return true;
            }
        });
        Texture texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IDLE_IMAGE, Texture.class, false);
        if (texture == null) {
            ActGame.gameInstance().getAssetManager().load(AssetConstant.SLIME_IDLE_IMAGE, Texture.class);
            ActGame.gameInstance().getAssetManager().finishLoading();
            texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.SLIME_IDLE_IMAGE, Texture.class);
        }
//        this.setWidth(getStage().getWidth());
//        this.setHeight(getStage().getHeight());
        System.out.println("width: " + getWidth() + " height: " + getHeight());
        slimeTexture = TextureRegion.split(texture, 64, 64)[0][0];
    }


    @Override
    public boolean handleMessage(Telegram msg) {
        if (msg.message == PRE_ADD_SLIME) {
            sign = !sign;
            System.out.println("sign: " + sign);
            return true;
        }
        return false;
    }

    Vector2 tmp = new Vector2();
    Color preColor = new Color();
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!sign) {
            return;
        }
        tmp.set(x,y);
//        getStage().stageToScreenCoordinates(tmp.set(x,y));
//        world.getStage().screenToStageCoordinates(tmp);
        preColor.set(batch.getColor());
        batch.flush();
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, batch.getColor().a * 0.25f);
        batch.draw(slimeTexture, tmp.x - slimeTexture.getRegionWidth() /2f, tmp.y - 16f - 8f);
//        batch.draw(keyFrame, actor.position.x - keyFrame.getRegionWidth() / 2f, actor.position.y - 16f - 8f);
//        batch.disableBlending();

        batch.flush();
        batch.setColor(preColor);

    }

}

package com.voidvvv.game.asset;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.state.bob.SelfStatus;

public class BobAssetConstant {
    public static final int IDLE = 0;


    public static TextureRegion[][] base_pic;
    public Animation<TextureRegion> idle_animation;
    public Animation<TextureRegion> spell_animation;

    public Animation<TextureRegion> walk_animation;
    public Animation<TextureRegion> attack_animation;
    public Animation<TextureRegion> dying_animation;


    public int xSplit = 32, ySplit = 32;

    public BobAssetConstant() {}

    public void init () {
        if (base_pic == null) {
            Texture texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.BOB_IMAGE, Texture.class);
            base_pic = TextureRegion.split(texture, xSplit, ySplit);

        }
        if (base_pic != null) {

            idle_animation = new Animation<>(0.1f, base_pic[0]);
            idle_animation.setPlayMode(Animation.PlayMode.LOOP);
            spell_animation = new Animation<>(0.1f, base_pic[1]);
            walk_animation = new Animation<>(0.1f, base_pic[2]);
            attack_animation = new Animation<>(0.1f, base_pic[3]);
            attack_animation.setPlayMode(Animation.PlayMode.LOOP);
            dying_animation = new Animation<>(0.1f, base_pic[4]);

        }
    }

    public TextureRegion currentAnim(int status, float statusTime) {
        if (status == IDLE) {
            return idle_animation.getKeyFrame(statusTime);
        }
        return idle_animation.getKeyFrame(statusTime);
    }


    public TextureRegion currentAnim(State status) {
        if (status == SelfStatus.IDLE) {
            return idle_animation.getKeyFrame(((SelfStatus)status).time);
        }
        return null;
    }
}

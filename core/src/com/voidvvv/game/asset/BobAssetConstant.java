package com.voidvvv.game.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.state.bob.BobStatus;
import com.voidvvv.game.base.test.Bob;

public class BobAssetConstant {
    public static final int IDLE = 0;


    public static TextureRegion[][] base_pic;
    public static TextureRegion[][] base_pic_mirror;

    public Animation<TextureRegion> idle_animation;
    public Animation<TextureRegion> idle_animation_mirror;
    public Animation<TextureRegion> spell_animation;
    public Animation<TextureRegion> spell_animation_mirror;

    public Animation<TextureRegion> walk_animation;
    public Animation<TextureRegion> walk_animation_mirror;

    public Animation<TextureRegion> attack_animation;
    public Animation<TextureRegion> attack_animation_mirror;

    public Animation<TextureRegion> dying_animation;
    public Animation<TextureRegion> dying_animation_mirror;


    public int xSplit = 32, ySplit = 32;

    public BobAssetConstant() {
    }

    public void init() {
        if (base_pic == null) {
            Texture texture = ActGame.gameInstance().getAssetManager().get(AssetConstant.BOB_IMAGE, Texture.class);
            base_pic = TextureRegion.split(texture, xSplit, ySplit);
            base_pic_mirror = TextureRegion.split(texture, xSplit, ySplit);

            for (int i = 0; i < base_pic_mirror.length; i++) {
                for (int j = 0; j < base_pic_mirror[i].length; j++) {
                    base_pic_mirror[i][j].flip(true, false);
                }
            }
        }
        if (base_pic != null) {

            idle_animation = AssetConstant.makeCommonAnimation(base_pic[0]);
            idle_animation.setPlayMode(Animation.PlayMode.LOOP);
            idle_animation_mirror = AssetConstant.makeCommonAnimation(base_pic_mirror[0]);
            idle_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            spell_animation = AssetConstant.makeCommonAnimation(base_pic[1]);
//            spell_animation.setPlayMode(Animation.PlayMode.LOOP);
            spell_animation_mirror = AssetConstant.makeCommonAnimation(base_pic_mirror[1]);
//            spell_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            walk_animation = AssetConstant.makeCommonAnimation(base_pic[2]);
            walk_animation.setPlayMode(Animation.PlayMode.LOOP);
            walk_animation_mirror = AssetConstant.makeCommonAnimation(base_pic_mirror[2]);
            walk_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            attack_animation = AssetConstant.makeCommonAnimation(base_pic[3]);
            attack_animation.setPlayMode(Animation.PlayMode.LOOP);
            attack_animation_mirror = AssetConstant.makeCommonAnimation(base_pic_mirror[3]);
            attack_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

            dying_animation = AssetConstant.makeCommonAnimation(base_pic[4]);
//            dying_animation.setPlayMode(Animation.PlayMode.LOOP);
            dying_animation_mirror = AssetConstant.makeCommonAnimation(base_pic_mirror[4]);
//            dying_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

        }
    }


    public TextureRegion currentAnim(Bob actor) {
        BobStatus status = actor.getSelfStatusStateMachine().getCurrentState();
        boolean flip = actor.flip;
        float time = actor.statusProgress;
        if (status == BobStatus.IDLE) {
            return flip ? idle_animation_mirror.getKeyFrame(time) : idle_animation.getKeyFrame(time);
        }
        if (status == BobStatus.WALKING) {
            return flip ? walk_animation_mirror.getKeyFrame(time) : walk_animation.getKeyFrame(time);
        }
        if (status == BobStatus.ATTACKING_0 ) {
            return flip ? attack_animation_mirror.getKeyFrame(time) : attack_animation.getKeyFrame(time);

        }

        if (status == BobStatus.SPELL_0) {
            Animation<TextureRegion> tr = flip ? spell_animation_mirror : spell_animation;

            return tr.getKeyFrame(tr.getAnimationDuration() * 1.0f);
        }

        if (status == BobStatus.DYING) {
            return flip? dying_animation_mirror.getKeyFrame(time) : dying_animation.getKeyFrame(time);
        }
        return null;
    }
}

package com.voidvvv.game.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.base.state.normal.DyingStatus;
import com.voidvvv.game.base.state.normal.Idle;
import com.voidvvv.game.base.state.normal.Spelling;
import com.voidvvv.game.base.state.normal.Walking;
import com.voidvvv.game.base.test.Bob;
import com.voidvvv.game.utils.ReflectUtil;

import java.sql.Ref;

public class BobAssetConstant {


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
            attack_animation.setPlayMode(Animation.PlayMode.NORMAL);
            attack_animation_mirror = AssetConstant.makeCommonAnimation(base_pic_mirror[3]);
            attack_animation_mirror.setPlayMode(Animation.PlayMode.NORMAL);

            dying_animation = AssetConstant.makeCommonAnimation(base_pic[4]);
//            dying_animation.setPlayMode(Animation.PlayMode.LOOP);
            dying_animation_mirror = AssetConstant.makeCommonAnimation(base_pic_mirror[4]);
//            dying_animation_mirror.setPlayMode(Animation.PlayMode.LOOP);

        }
    }


    public TextureRegion currentAnim(Bob actor) {
        VCharactorStatus status = actor.getStateMachine().getCurrentState();
        boolean flip = actor.flipX;
        float time = actor.statusProgress;
        if (ReflectUtil.cast(status, Idle.class) != null) {
            return flip ? idle_animation_mirror.getKeyFrame(time) : idle_animation.getKeyFrame(time);
        }
        if (ReflectUtil.cast(status, Walking.class) != null) {
            return flip ? walk_animation_mirror.getKeyFrame(time) : walk_animation.getKeyFrame(time);
        }
//        if (status == BobStatus.ATTACKING_0 ) {
//            return flip ? attack_animation_mirror.getKeyFrame(time, false) : attack_animation.getKeyFrame(time, false);
////            return null;
//        }
//
        if (ReflectUtil.cast(status, Spelling.class) != null) {
            Animation<TextureRegion> tr = flip ? spell_animation_mirror : spell_animation;
            float percent = actor.statusProgress;
            return tr.getKeyFrame(tr.getAnimationDuration() * percent);
        }
//
        if (ReflectUtil.cast(status, DyingStatus.class) != null) {
            return flip? dying_animation_mirror.getKeyFrame(time) : dying_animation.getKeyFrame(time);
        }
        return null;
    }
}

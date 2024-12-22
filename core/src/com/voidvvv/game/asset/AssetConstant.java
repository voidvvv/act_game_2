package com.voidvvv.game.asset;

import com.badlogic.gdx.graphics.g2d.Animation;

public class AssetConstant {
    // map
    public static final String MAP_TEST_01 = "map/test/act_game_02.tmx";
    // image
    public static final String BOB_IMAGE = "image/actor/bob/wizard spritesheet calciumtrice.png";
    public static final String STONE_MAN = "image/actor/stone/stone_man.png";
    public static final String SLIME_IMAGE = "image/actor/slime/Slime1_Attack_full.png";
    public static final String SLIME_IDLE_IMAGE = "image/actor/slime/Slime1_Idle_full.png";
    public static final String SLIME_WALK_IMAGE = "image/actor/slime/Slime1_Walk_full.png";
    public static final String SLIME_DYING_IMAGE = "image/actor/slime/Slime1_Death_body.png";

    //audio
    public static final String GRASS_STEP_LEFT_SOUND = "sound/step/grass/sfx_step_grass_l.flac";
    public static final String GRASS_STEP_RIGHT_SOUND = "sound/step/grass/sfx_step_grass_r.flac";


    public static <T> Animation<T> makeCommonAnimation (T... arr) {
        return new Animation<>(1f/arr.length, arr);
    }

    public static <T> Animation<T> makeCommonAnimation (float time, T... arr) {
        return new Animation<>(time/arr.length, arr);
    }
}

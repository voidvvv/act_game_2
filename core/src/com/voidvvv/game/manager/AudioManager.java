package com.voidvvv.game.manager;

import com.badlogic.gdx.audio.Sound;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;

public class AudioManager {
    private Sound grassStepLeft;
    private Sound grassStepRight;


    public AudioManager () {

    }

    public void init () {

    }

    public long playGrassStepLeft () {
        if (grassStepLeft == null) {
            grassStepLeft = ActGame.gameInstance().getAssetManager().get(AssetConstant.GRASS_STEP_LEFT_SOUND, Sound.class);
        }
        if (grassStepLeft != null) {
            return grassStepLeft.play();
        }
        return -1;
    }
    public long playGrassStepRight () {
        if (grassStepRight == null) {
            grassStepRight = ActGame.gameInstance().getAssetManager().get(AssetConstant.GRASS_STEP_RIGHT_SOUND, Sound.class);
        }
        if (grassStepRight != null) {
            return grassStepRight.play();
        }
        return -1;
    }
}

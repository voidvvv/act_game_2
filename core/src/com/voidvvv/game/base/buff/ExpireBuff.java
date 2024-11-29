package com.voidvvv.game.base.buff;

import com.badlogic.gdx.Gdx;

public abstract class ExpireBuff extends Buff {
    public float time = 0;
    @Override
    public boolean expire() {
        return time <= 0;
    }

    @Override
    public void update() {
        time -= Gdx.graphics.getDeltaTime();
    }
}

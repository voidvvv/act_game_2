package com.voidvvv.game.base.buff;

import com.badlogic.gdx.Gdx;

public class BasicAttrIncreaseBuff extends Buff{
    public float time;

    @Override
    public BuffOrder getOrder() {
        return BuffOrder.ATTR_PLUS;
    }

    @Override
    public void enter() {

    }

    @Override
    public void update() {
        time -= Gdx.graphics.getDeltaTime();
    }

    @Override
    public void exit() {

    }
}

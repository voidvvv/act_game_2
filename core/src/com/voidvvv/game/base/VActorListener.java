package com.voidvvv.game.base;

import com.badlogic.gdx.utils.Pool;

public interface VActorListener extends Pool.Poolable {

    int order();

    void afterBecomeDying();

    void afterBeDamage();

    void afterMakeDamage();

    void afterMakeAttackEvent();

    void afterBeAttackEvent();


    void afterUseSkill();
}

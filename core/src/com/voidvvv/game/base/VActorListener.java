package com.voidvvv.game.base;

import com.badlogic.gdx.utils.Pool;

/**
 * do some <b>extra</b> behavior when something happen
 */
public interface VActorListener extends Pool.Poolable {

    int order();

    void afterBecomeDying();

    void afterBeDamage();

    void afterMakeDamage();

    void afterMakeAttackEvent();

    void afterBeAttackEvent();

    void preUserSkill();

    void afterUseSkill();

    void afterHitOnActor();

    void afterHitOver();

    void afterAddBuff();

    void afterMove();

    void setOwner(VActor owner);

    VActor getOwner();

}

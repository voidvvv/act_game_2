package com.voidvvv.game.base.skill;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.VCharacter;

public interface Skill extends Pool.Poolable {

    int type();

    float percentage();

    int init(VCharacter character);

    VCharacter owner();

    void setOwner(VCharacter owner);

    boolean couldBeReplace(Skill skill);

    void start();

    void process(float delta);

    void end();

    boolean isEnding();


    Cost cost();

    void onMove();
}

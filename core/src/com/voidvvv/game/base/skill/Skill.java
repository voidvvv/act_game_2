package com.voidvvv.game.base.skill;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.VSkillCharacter;

public interface Skill extends Pool.Poolable {

    int type();

    int init(VSkillCharacter character);

    VSkillCharacter owner();

    void setOwner(VSkillCharacter owner);

    boolean couldBeReplace(Skill skill);

    void start();

    void process(float delta);

    void end();

    boolean isEnding();
}

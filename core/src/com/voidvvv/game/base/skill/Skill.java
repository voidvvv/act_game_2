package com.voidvvv.game.base.skill;

import com.voidvvv.game.base.VSkillCharacter;

public interface Skill {

    int type();

    VSkillCharacter owner();

    void setOwner(VSkillCharacter owner);

    void start();

    void process(float delta);
}

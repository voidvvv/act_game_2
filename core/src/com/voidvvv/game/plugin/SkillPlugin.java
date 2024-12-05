package com.voidvvv.game.plugin;

import com.voidvvv.game.base.skill.v2.Skill;

public abstract class SkillPlugin implements Plugin {
    public Skill skill;

    @Override
    public void reset() {
        this.skill = null;
    }
}

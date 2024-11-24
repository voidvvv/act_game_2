package com.voidvvv.game.base;

import com.voidvvv.game.base.skill.Skill;
import lombok.Getter;
import lombok.Setter;

public class VSkillCharacter extends VCharacter{
    public static final int SKILL_TYPE_Q = 1;
    public static final int SKILL_TYPE_W = 2;
    public static final int SKILL_TYPE_E = 3;
    public static final int SKILL_TYPE_R = 4;

    @Getter
    protected Skill[] skills = new Skill[10];
    @Override
    protected void otherApply(float delta) {
        super.otherApply(delta);
    }

    public void bindSkill (Skill skill, int type) {
        if (skill == null) {
            return;
        }
        if (type < 0 || type >= skills.length) {
            return;
        }
        skills[type] = skill;
        skill.setOwner(this);
    }


}

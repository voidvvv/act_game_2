package com.voidvvv.game.base;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.base.skill.SkillDes;
import lombok.Getter;

import java.util.Stack;

public class VSkillCharacter extends VCharacter{
    public static final int SKILL_TYPE_Q = 1;
    public static final int SKILL_TYPE_W = 2;
    public static final int SKILL_TYPE_E = 3;
    public static final int SKILL_TYPE_R = 4;

    @Getter
    protected SkillDes[] skills = new SkillDes[10];

    @Getter
    protected Stack<Skill> skillQueue = new Stack<>();

    public boolean skillNew = false;
    @Getter
    protected Skill currentSkill;

    @Override
    protected void otherApply(float delta) {
        super.otherApply(delta);
        if (currentSkill == null && !skillQueue.isEmpty()) {
            useNewSkill(skillQueue.pop());
        }
    }

    protected void useNewSkill(Skill skill) {
        this.currentSkill = skill;
        skillNew = true;
        afterApplyNewSkill();
    }

    public void over(Skill skill) {
        if (this.currentSkill == skill) {
            this.currentSkill = null;
        }
//        Pools.free(skill);
    }

    public void bindSkill (SkillDes skill, int type) {
        if (skill == null) {
            return;
        }
        if (type < 0 || type >= skills.length) {
            return;
        }
        skills[type] = skill;
    }

    public boolean tryToUseSkill (Skill skill) {
        if (couldApplyNewSkill(skill)) {
            useNewSkill(skill);
            skillQueue.clear();
            return true;
        } else if (couldWait(skill)) {
            skillQueue.push(skill);
        } else {
            Pools.free(skill);
        }
        return false;
    }

    protected boolean couldWait(Skill skill) {
        return false;
    }

    protected boolean couldApplyNewSkill(Skill skill) {
        return currentSkill == null || currentSkill.couldBeReplace(skill);
    }

    protected void afterApplyNewSkill() {
        // change status and send message
    }

    public Skill currentSkill () {
        return currentSkill;
    }

    public void tryToBackToNormal() {

    }
}

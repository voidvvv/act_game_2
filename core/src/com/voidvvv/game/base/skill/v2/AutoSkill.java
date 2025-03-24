package com.voidvvv.game.base.skill.v2;

public abstract class AutoSkill extends Skill{
    @Override
    public void update(float delta) {
        super.update(delta);
        if (this.cooldown == 0f) {
            use();
        }
    }

    @Override
    public SkillType skillType() {
        return SkillType.NOOP;
    }
}

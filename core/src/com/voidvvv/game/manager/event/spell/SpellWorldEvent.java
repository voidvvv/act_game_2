package com.voidvvv.game.manager.event.spell;


import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.skill.v2.Skill;
import com.voidvvv.game.manager.event.WorldEvent;
import com.voidvvv.game.utils.ReflectUtil;
import lombok.Setter;

public class SpellWorldEvent extends WorldEvent {

    @Setter
    Skill skill;

    public SpellWorldEvent() {
    }

    public boolean shouldDoPost = false;

    @Override
    public void apply() {
        if (skill != null) {
            this.shouldDoPost = skill.shouldDoPost;
            skill.does();
        }
        this.status = WorldEvent.FINISH;
    }

    @Override
    public void postApply() {
        VCharacter vch = null;
        if (shouldDoPost && (vch = ReflectUtil.cast(this.fromActor, VCharacter.class)) != null) {
            vch.postUseSkill(skill);
        }
    }

    @Override
    public void reset() {
        super.reset();
        shouldDoPost = false;
        skill = null;
    }

    @Override
    public boolean isEnd() {
        return this.status == WorldEvent.FINISH;
    }
}

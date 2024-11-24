package com.voidvvv.game.manager.event.spell;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.manager.event.WorldEvent;
import lombok.Getter;
import lombok.Setter;

public  class SpellWorldEvent extends WorldEvent {
    @Setter @Getter
    protected Skill skill;

    public SpellWorldEvent() {}

    @Override
    public void apply() {
        skill.start();
    }

    @Override
    public void postApply() {

    }

    @Override
    public void reset() {
        super.reset();
        this.setSkill(null);
    }
}

package com.voidvvv.game.manager.event.spell;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.base.skill.SkillDes;
import com.voidvvv.game.manager.event.WorldEvent;
import com.voidvvv.game.utils.ReflectUtil;
import lombok.Getter;
import lombok.Setter;

public  class SpellWorldEvent extends WorldEvent {
    @Setter @Getter
    protected SkillDes skillDes;

    public SpellWorldEvent() {}

    @Override
    public void apply() {
        if (skillDes == null) {
            this.status = WorldEvent.FINISH;
            return;
        }
        Class<? extends Skill> skillClass = skillDes.getSkillClass();
        Skill obtain = Pools.obtain(skillClass);
        VSkillCharacter skillCharacter = ReflectUtil.cast(fromActor, VSkillCharacter.class);
        if (skillCharacter != null) {
            obtain.setOwner(skillCharacter);
            skillCharacter.tryToUseSkill(obtain);
        }
        postApply();
        this.status = WorldEvent.FINISH;
    }

    @Override
    public void postApply() {

    }

    @Override
    public void reset() {
        super.reset();
        this.setSkillDes(null);
    }

    @Override
    public boolean isEnd() {
        return this.status == WorldEvent.FINISH;
    }
}

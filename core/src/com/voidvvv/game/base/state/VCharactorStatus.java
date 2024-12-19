package com.voidvvv.game.base.state;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.skill.v2.Skill;
import com.voidvvv.game.utils.ReflectUtil;

public abstract class VCharactorStatus implements State<VCharacter> {


    @Override
    public void update(VCharacter entity) {
        entity.execStatus(this);
    }


    public abstract void exec(VCharacter entity);

    @Override
    public boolean onMessage(VCharacter entity, Telegram telegram) {
        int message = telegram.message;
        if (message == ActorConstants.MSG_ACTOR_BASE_VELOCITY_CHANGE) {
            onMoveChange(entity, telegram);
        }else if (message == ActorConstants.MSG_ACTOR_AFTER_BE_HIT) {
            onHit(entity, telegram);
        } else if (message == ActorConstants.MSG_ACTOR_ATTEMPT_TO_SPELL) {
            onAttemptSpell(entity, telegram);
        } else if (message == ActorConstants.MSG_ACTOR_PRE_ATTEMPT_TO_SPELL) {
            preAttemptSpell(entity, telegram);
        }

        return false;
    }

    public void preAttemptSpell(VCharacter entity, Telegram telegram) {
        StatusData sd = ReflectUtil.cast(telegram.extraInfo, StatusData.class);
        Skill lastPreSkill = entity.lastPreSkill;
        if (lastPreSkill == null) {
            return;
        }
        Skill.SkillType skillType = lastPreSkill.skillType();
        skillType.applySkillType(this, entity, telegram);


    }

    public void defaultReject(VCharacter entity, Telegram telegram) {
        StatusData sd = ReflectUtil.cast(telegram.extraInfo, StatusData.class);
        if (sd != null) {
            sd.setSuccess(false);
        }
    }

    public void onPhysicSpell(VCharacter entity, Telegram telegram) {
        defaultReject(entity, telegram);
    }

    public void onRotateSpell(VCharacter entity, Telegram telegram) {
        defaultReject(entity, telegram);

    }

    public void onRangeSpell(VCharacter entity, Telegram telegram) {
        defaultReject(entity, telegram);

    }

    public void onMagicSpell(VCharacter entity, Telegram telegram) {
        defaultReject(entity, telegram);

    }

    public void onA_Attack(VCharacter entity, Telegram telegram) {
        defaultReject(entity, telegram);

    }

    public void onAttemptSpell(VCharacter entity, Telegram telegram) {
        defaultReject(entity, telegram);

    }

    protected void onMoveChange(VCharacter entity, Telegram telegram) {

    }

    protected void onHit(VCharacter entity, Telegram telegram) {

    }

    public void onNoopSkill(VCharacter character, Telegram gram) {

    }
}

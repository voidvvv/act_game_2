package com.voidvvv.game.base.state;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.ActorConstants;

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
        }

        return false;
    }

    private void onAttemptSpell(VCharacter entity, Telegram telegram) {

    }

    private void onMoveChange(VCharacter entity, Telegram telegram) {

    }

    private void onHit(VCharacter entity, Telegram telegram) {

    }
}

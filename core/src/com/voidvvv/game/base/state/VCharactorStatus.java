package com.voidvvv.game.base.state;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.VCharacter;

public abstract class VCharactorStatus implements State<VCharacter> {

    @Override
    public void enter(VCharacter entity) {

    }

    @Override
    public void update(VCharacter entity) {
        entity.execStatus(this);
    }

    @Override
    public void exit(VCharacter entity) {

    }

    public abstract void exec(VCharacter entity);

    @Override
    public boolean onMessage(VCharacter entity, Telegram telegram) {
        return false;
    }
}

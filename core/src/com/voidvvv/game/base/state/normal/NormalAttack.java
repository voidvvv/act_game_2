package com.voidvvv.game.base.state.normal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.state.VCharactorStatus;

public class NormalAttack extends VCharactorStatus {
    public static final NormalAttack INSTANCE = new NormalAttack();
    @Override
    public void exec(VCharacter entity) {
        entity.statusTime += Gdx.graphics.getDeltaTime();
        if (entity.statusProgress >= 1f) {
            entity.getStateMachine().changeState(Idle.INSTANCE);
        }
    }

    @Override
    public void enter(VCharacter entity) {
        entity.statusTime = 0f;
        entity.statusProgress = 0f;
    }

    @Override
    public void exit(VCharacter entity) {
        Gdx.app.log("NormalAttack", "exit statusProgress: " + entity.statusProgress);
        entity.statusTime = 0f;
        entity.statusProgress = 0f;
    }

    protected void exitCurrentProcess(VCharacter entity, Telegram telegram) {
        entity.getStateMachine().changeState(Idle.INSTANCE);
    }
}

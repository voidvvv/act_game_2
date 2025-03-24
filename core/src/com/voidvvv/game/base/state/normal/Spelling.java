package com.voidvvv.game.base.state.normal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.state.VCharactorStatus;

public class Spelling extends VCharactorStatus {
    public static final Spelling INSTANCE = new Spelling();
    @Override
    public void exec(VCharacter entity) {
        if (entity.statusProgress >= 1f) {
            entity.getStateMachine().changeState(Idle.INSTANCE);
            return;
        }
        entity.statusTime += Gdx.graphics.getDeltaTime();
//        entity.statusProgress += Gdx.graphics.getDeltaTime();

    }

    @Override
    public void enter(VCharacter entity) {
        Gdx.app.log("Spelling", entity.getName() + " dying!");
        entity.statusTime = 0f;
        entity.statusProgress = 0f;

    }

    @Override
    public void exit(VCharacter entity) {
        entity.statusTime = 0f;
        entity.statusProgress = 0f;
        System.out.println("exit Spelling");
    }

    protected void exitCurrentProcess(VCharacter entity, Telegram telegram) {
        entity.getStateMachine().changeState(Idle.INSTANCE);
    }
}

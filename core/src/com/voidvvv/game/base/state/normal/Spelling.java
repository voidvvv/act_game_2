package com.voidvvv.game.base.state.normal;

import com.badlogic.gdx.Gdx;
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
        entity.statusTime = 0f;
        entity.statusProgress = 0f;

    }

    @Override
    public void exit(VCharacter entity) {
        System.out.println("exit Spelling");
    }
}

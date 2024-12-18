package com.voidvvv.game.base.state.normal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.Telegram;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.state.VCharactorStatus;

public class Idle extends VCharactorStatus {
    public static final Idle INSTANCE = new Idle();
    private Idle(){}

    @Override
    public void exec(VCharacter entity) {
        entity.statusTime += Gdx.graphics.getDeltaTime();
        entity.statusProgress += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void enter(VCharacter entity) {

        entity.statusTime = 0f;
        entity.statusProgress = 0f;
    }

    @Override
    public void exit(VCharacter entity) {
        entity.statusTime = 0f;
        entity.statusProgress = 0f;
    }

    @Override
    protected void onMoveChange(VCharacter entity, Telegram telegram) {
        entity.getStateMachine().changeState(Walking.INSTANCE);
    }

    @Override
    public void onPhysicSpell(VCharacter entity, Telegram telegram) {
        super.onPhysicSpell(entity, telegram);
    }

    @Override
    public void onMagicSpell(VCharacter entity, Telegram telegram) {
        entity.getStateMachine().changeState(Spelling.INSTANCE);
    }

    @Override
    public void onRotateSpell(VCharacter entity, Telegram telegram) {
        super.onRotateSpell(entity, telegram);
    }

    @Override
    public void onA_Attack(VCharacter entity, Telegram telegram) {
        super.onA_Attack(entity, telegram);
    }

    @Override
    public void onAttemptSpell(VCharacter entity, Telegram telegram) {
        super.onAttemptSpell(entity, telegram);
    }

    @Override
    public void preAttemptSpell(VCharacter entity, Telegram telegram) {
        super.preAttemptSpell(entity, telegram);
    }
}

package com.voidvvv.game.base.actors.slime;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.base.state.normal.Idle;
import com.voidvvv.game.render.actor.VActorRender;

public class Slime extends VCharacter {

    StateMachine<VCharacter, VCharactorStatus> defalutStateMachine;
    float btUpdateStep = 0.1f;
    float btrCurrentStep = 0f;

    public VActorRender<Slime> render ;

    @Override
    public void init() {
        super.init();
        this.actorType = ActorConstants.ACTOR_TYPE_CHARACTER;
        if (defalutStateMachine == null) {
            defalutStateMachine = new DefaultStateMachine<>(this, Idle.INSTANCE);
        } else {
            defalutStateMachine.setInitialState(Idle.INSTANCE);
        }

    }

    @Override
    public void vCAct(float delta) {
        super.vCAct(delta);
        btrCurrentStep += delta;
        if (btrCurrentStep > btUpdateStep) {
            btrCurrentStep = 0f;
        }
        stateUpdate(delta);
    }

    private void stateUpdate(float delta) {
        defalutStateMachine.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (render == null) {
            super.draw(batch, parentAlpha);
        } else {
            render.render(this, batch, parentAlpha);
        }
    }

    @Override
    public void useSkill(int skillCode) {

    }

    @Override
    public void reset() {
        super.reset();
    }


    @Override
    public StateMachine<VCharacter, VCharactorStatus> getStateMachine() {
        return this.defalutStateMachine;
    }
}

package com.voidvvv.game.base.actors;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.btree.BTManager;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.state.slime.SlimeStatus;
import com.voidvvv.game.render.actor.VActorRender;

public class Slime extends VCharacter {

    StateMachine<Slime, State<Slime>> defalutStateMachine;
    float btUpdateStep = 0.1f;
    float btrCurrentStep = 0f;

    public VActorRender<Slime> render ;

    @Override
    public void init() {
        super.init();
        this.actorType = ActorConstants.ACTOR_TYPE_CHARACTER;
        if (defalutStateMachine == null) {
            defalutStateMachine = new DefaultStateMachine<>(this, SlimeStatus.IDEL);
        }

    }

    public StateMachine<Slime, State<Slime>> getDefalutStateMachine() {
        return defalutStateMachine;
    }

    @Override
    public void vCAct(float delta) {
        super.vCAct(delta);
        btrCurrentStep += delta;
        if (btrCurrentStep > btUpdateStep) {
            btrCurrentStep = 0f;
        }
//        System.out.println(defalutStateMachine.getCurrentState().toString());
//        defalutStateMachine.changeState(SlimeStatus.IDEL);
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
    protected void becomeDying() {
        this.defalutStateMachine.changeState(SlimeStatus.DYING);
    }

    @Override
    public boolean isDying() {
        return this.defalutStateMachine.getCurrentState() == SlimeStatus.DYING;
    }

}

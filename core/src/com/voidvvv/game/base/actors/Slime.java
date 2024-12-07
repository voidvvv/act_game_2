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

public class Slime extends VCharacter {

    StateMachine<Slime, State<Slime>> defalutStateMachine;
    float btUpdateStep = 0.1f;
    float btrCurrentStep = 0f;

    @Override
    public void init() {
        super.init();
        VCube cube = new VCube();
        cube.xLength = this.physicAttr.box2dHx * 2f;
        cube.yLength = this.physicAttr.box2dHy * 2f;
        cube.zLength = getWorld().unit() *2;
        physicAttr.setBaseShape(cube);

        if (defalutStateMachine == null) {
            defalutStateMachine = new DefaultStateMachine<>(this);
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

//        defalutStateMachine.changeState(SlimeStatus.IDEL);
        stateUpdate(delta);
    }

    private void stateUpdate(float delta) {
        defalutStateMachine.update();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
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

package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.slime.Slime;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.context.input.InputActionMapping;
import com.voidvvv.game.utils.ReflectUtil;

public class HitTarget extends LeafTask<Slime> {

    @Override
    public void start() {
        getObject().setFrameSkill(InputActionMapping.SKILL_Q);
        getObject().setHorizonVelocity(0f,0f);
        super.start();
    }

    @Override
    public void end() {
        super.end();
    }

    @Override
    public Status execute() {
        Slime object = getObject();
        getObject().statusTime += ActGame.gameInstance().getBtManager().stepInterval;
//        VCharactorStatus currentState = object.getStateMachine().getCurrentState();
        if (object.statusProgress >= 1f) {
            return Status.SUCCEEDED;
        }
        return Status.RUNNING;
    }

    @Override
    protected Task<Slime> copyTo(Task<Slime> task) {
        return task;
    }
}

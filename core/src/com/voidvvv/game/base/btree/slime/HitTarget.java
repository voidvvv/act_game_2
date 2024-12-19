package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.slime.Slime;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.context.input.InputActionMapping;

public class HitTarget extends LeafTask<Slime> {

    @Override
    public void start() {
        getObject().setFrameSkill(InputActionMapping.SKILL_Q);
        super.start();
    }

    @Override
    public void end() {
        super.end();
    }

    @Override
    public Status execute() {
        return null;
    }

    @Override
    protected Task<Slime> copyTo(Task<Slime> task) {
        return task;
    }
}

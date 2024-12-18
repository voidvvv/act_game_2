package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.MathUtils;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.actors.slime.Slime;

public class Trance extends LeafTask<Slime> {
    float tranceTime = 0f;
    float taskTime = 0f;
    @Override
    public void end() {
        taskTime = 0f;
        super.end();
    }

    @Override
    public void start() {
        tranceTime = MathUtils.random(0.75f, 2.5f);
        taskTime = 0;
        super.start();
    }

    @Override
    public Status execute() {

        if (taskTime >= tranceTime) {
            return Status.SUCCEEDED;
        }
        getObject().setHorizonVelocity(0f,0f);
        taskTime += ActGame.gameInstance().getBtManager().stepInterval;
        return Status.RUNNING;
    }

    @Override
    protected Task<Slime> copyTo(Task<Slime> task) {
        return task;
    }
}

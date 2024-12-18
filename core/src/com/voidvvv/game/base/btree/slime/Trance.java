package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.voidvvv.game.base.actors.slime.Slime;

public class Trance extends LeafTask<Slime> {

    @Override
    public void end() {
        super.end();
    }

    @Override
    public void start() {
        super.start();
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

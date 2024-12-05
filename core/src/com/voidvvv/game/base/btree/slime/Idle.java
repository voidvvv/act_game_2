package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.actors.Slime;
import com.voidvvv.game.context.VWorld;

public class Idle  extends LeafTask<Slime> {
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void end() {
        super.end();
    }

    @Override
    public Status execute() {
        Slime slime = getObject();
        VWorld world = slime.getWorld();
        VActor protagonist = world.getProtagonist();
        float mx = protagonist.position.x;
        float my = protagonist.position.y;

        slime.findPath(mx, my);
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Slime> copyTo(Task<Slime> task) {
        Task<Slime> result = new Idle();

        return result;
    }
}

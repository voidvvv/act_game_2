package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.actors.Slime;
import com.voidvvv.game.context.VWorld;

public class Idle  extends LeafTask<Slime> {
    float lastX=0;
    float lastY=0;

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

        if (mx != lastX || my != lastY) {
            slime.findPath(mx, my);
            lastX = mx;
            lastY = my;
        }
        return Status.SUCCEEDED;
    }

    @Override
    protected Task<Slime> copyTo(Task<Slime> task) {


        return task;
    }

    @Override
    public void reset() {
        this.lastX = 0f;
        this.lastY = 0f;
        super.reset();
    }



}

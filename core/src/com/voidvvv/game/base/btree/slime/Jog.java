package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.actors.slime.Slime;

public class Jog extends LeafTask<Slime> {
    float timeStamp = 0f;

    Vector2 dir = new Vector2();
    public static final int NO_DIR = 0;
    public static final int WALKING = 1;

    float maxWalkingTime = 2f;

    float currentWalkingTime = 0f;
    int status = NO_DIR;

    @Override
    public void start() {

        super.start();
        timeStamp = ActGame.gameInstance().totalGameTime;
        currentWalkingTime = 0f;
        status = NO_DIR;

        float random = MathUtils.random(2.f);
        float radian = (float)Math.PI * random;
        dir.x = MathUtils.cos(radian);
        dir.y = MathUtils.sin(radian);
        currentWalkingTime = 0f;
        maxWalkingTime = MathUtils.random(0.5f, 2.5f);
        status = WALKING;
    }

    @Override
    public void end() {
        super.end();
    }


    @Override
    public Status execute() {

        getObject().baseMove.x = dir.x;
        getObject().baseMove.y = dir.y;
        currentWalkingTime += (ActGame.gameInstance().getBtManager().stepInterval);
        if (currentWalkingTime > maxWalkingTime) {
            return Status.SUCCEEDED;
        }
        timeStamp = ActGame.gameInstance().totalGameTime;
        return Status.RUNNING;
    }

    @Override
    protected Task<Slime> copyTo(Task<Slime> task) {


        return task;
    }

    @Override
    public void reset() {
        super.reset();

    }



}

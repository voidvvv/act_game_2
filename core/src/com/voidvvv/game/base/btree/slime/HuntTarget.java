package com.voidvvv.game.base.btree.slime;

import com.badlogic.gdx.ai.btree.LeafTask;
import com.badlogic.gdx.ai.btree.Task;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.NormalDetector;
import com.voidvvv.game.base.actors.slime.Slime;

public class HuntTarget extends LeafTask<Slime> {
    float maxHuntDistance;

    @Override
    public void end() {
        super.end();
        getObject().interruptPathFinding();
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void start() {
        maxHuntDistance = MathUtils.random(50f, 150f);
        NormalDetector normalDetector = getObject().getNormalDetector();
        VCharacter target = normalDetector.getTarget();
        if (normalDetector == null || target == null) {
            return ;
        }
        lastTargetX = target.position.x;
        lastTargetY = target.position.y;
        getObject().findPath(lastTargetX,lastTargetY);
        super.start();
    }

    float lastTargetX = 0f;
    float lastTargetY = 0f;
    Vector2 tmpV2 = new Vector2();
    @Override
    public Status execute() {
        Slime slime = getObject();
        NormalDetector normalDetector = slime.getNormalDetector();
        if (normalDetector == null || normalDetector.getTarget() == null) {
            return Status.FAILED;
        }
        VCharacter target = normalDetector.getTarget();

        Vector3 tPosition = target.position;
        Vector3 sPosition = slime.position;
        float distance = tmpV2.set(tPosition.x - sPosition.x, tPosition.y - sPosition.y).len();
        if (distance >= maxHuntDistance) {
            return Status.FAILED;
        }
        float c = 1.5f;
        float box2dHx = slime.physicAttr.box2dHx;
        float box2dHy = slime.physicAttr.box2dHy;
        float box2dHz = slime.physicAttr.box2dHz;
        if (distance <= Vector2.len(box2dHx, box2dHz - box2dHy) * c) {
            return Status.SUCCEEDED;
        }
        if (MathUtils.isEqual(target.position.x, lastTargetX) && MathUtils.isEqual(target.position.y, lastTargetY)) {
            return Status.SUCCEEDED;
        }
        slime.findPath(target.position.x, target.position.y);
        return Status.RUNNING;
    }

    @Override
    protected Task<Slime> copyTo(Task<Slime> task) {
        return task;
    }
}

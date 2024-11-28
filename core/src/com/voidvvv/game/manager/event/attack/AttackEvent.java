package com.voidvvv.game.manager.event.attack;

import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.manager.behaviors.Behavior;
import com.voidvvv.game.manager.event.WorldEvent;

public abstract class AttackEvent extends WorldEvent implements AttackCalculator {

    protected VActor triggerObj;

    public VActor getTriggerObj() {
        return triggerObj;
    }

    public void setTriggerObj(VActor triggerObj) {
        this.triggerObj = triggerObj;
    }

    @Override
    public void apply() {
        if (this.status == WorldEvent.INIT_STATUS) {
            spawnAndAttach();
        } else if (shouldDoPost()) {
            postApply();
            this.status = WorldEvent.FINISH;
        } else if (shouldStop()){
            this.status = WorldEvent.FINISH;
        } else {
            // wait
        }
    }

    protected abstract boolean shouldStop();
    protected abstract void spawnAndAttach();

    protected abstract boolean shouldDoPost();

    @Override
    public void postApply() {
        fromActor.postAttack(this);
        targetActor.postBeAttacked(this);
    }

    @Override
    public void reset() {
        super.reset();
    }
}
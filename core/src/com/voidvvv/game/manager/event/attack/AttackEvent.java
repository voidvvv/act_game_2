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

    Behavior behavior;
    @Override
    public void apply() {
        if (this.status == WorldEvent.INIT_STATUS) {
            behavior = calculate(fromActor, targetActor);
            // attach attack behavior to target
            targetActor.attachBehavior(behavior);
            this.status = WorldEvent.ATTACHED;
        } else if (this.status == WorldEvent.ATTACHED) {
            postApply();
            this.status = WorldEvent.FINISH;
        }
    }

    @Override
    public void postApply() {
        fromActor.postAttack(this);
        targetActor.postBeAttacked(this);
    }

    @Override
    public void reset() {
        super.reset();
        behavior= null;
    }
}
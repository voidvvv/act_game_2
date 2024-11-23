package com.voidvvv.game.manager.event.attack;

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
        behavior = calculate(fromActor, targetActor);
        // attach attack behavior to target
        targetActor.attachBehavior(behavior);
    }

    @Override
    public void postApply() {
        fromActor.postAttack(behavior);
        targetActor.postBeAttacked(behavior);
    }

    @Override
    public void reset() {
        super.reset();
        behavior= null;
    }
}
package com.voidvvv.game.manager.event.attack;

import com.voidvvv.game.base.VActor;
import com.voidvvv.game.manager.behaviors.BeAttackBehavior;
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
        Behavior calculate = calculate(fromActor, targetActor);
        // attach attack behavior to target
        targetActor.attachBehavior(calculate);
    }

    @Override
    public void postApply() {

    }
}
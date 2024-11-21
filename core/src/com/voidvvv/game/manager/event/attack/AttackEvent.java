package com.voidvvv.game.manager.event.attack;

import com.voidvvv.game.manager.behaviors.BeAttackBehavior;
import com.voidvvv.game.manager.event.WorldEvent;

public abstract class AttackEvent extends WorldEvent implements AttackCalculator {


    @Override
    public void apply() {
        BeAttackBehavior calculate = calculate(fromActor, targetActor);
        // attach attack behavior to target
        targetActor.attachBehavior(calculate);
    }

}
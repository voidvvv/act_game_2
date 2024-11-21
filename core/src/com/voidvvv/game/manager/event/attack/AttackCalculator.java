package com.voidvvv.game.manager.event.attack;

import com.voidvvv.game.base.VActor;
import com.voidvvv.game.manager.behaviors.BeAttackBehavior;
import com.voidvvv.game.manager.behaviors.Behavior;

public interface AttackCalculator {

    Behavior calculate(VActor from, VActor to);
}

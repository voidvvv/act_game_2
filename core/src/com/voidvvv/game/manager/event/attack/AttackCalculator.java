package com.voidvvv.game.manager.event.attack;

import com.voidvvv.game.base.VActor;
import com.voidvvv.game.manager.behaviors.BeAttackBehavior;

public interface AttackCalculator {

    BeAttackBehavior calculate(VActor from, VActor to);
}

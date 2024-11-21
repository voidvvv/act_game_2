package com.voidvvv.game.manager.event.attack;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.context.BattleContext;
import com.voidvvv.game.manager.behaviors.BeAttackBehavior;
import com.voidvvv.game.manager.event.VWorldEventManager;
import com.voidvvv.game.utils.ReflectUtil;

public class BasePhysicAttackEvent extends AttackEvent{

    @Override
    public BeAttackBehavior calculate(VActor from, VActor to) {
        VCharacter fromChar = ReflectUtil.cast(from, VCharacter.class);
        VCharacter toChar = ReflectUtil.cast(to, VCharacter.class);
        if (fromChar != null && toChar != null) {
            BattleAttr fromBattle = fromChar.getActualBattleAttrWithoutCheck();
            BattleAttr toBattle = toChar.getActualBattleAttrWithoutCheck();

            BeAttackBehavior behavior = Pools.obtain(BeAttackBehavior.class);
            behavior.setFrom(from);
            behavior.setTo(to);
            behavior.setTrigger(getExtraInfo());
            behavior.setAttackType(BattleContext.AttackType.ATTACK_PHYSIC);
            behavior.setDamage(fromBattle.attack);
            to.getWorld().getBattleContext().fixAttack(behavior);
            return behavior;
        }
        return null;
    }
}

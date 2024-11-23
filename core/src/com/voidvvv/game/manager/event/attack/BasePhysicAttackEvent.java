package com.voidvvv.game.manager.event.attack;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.context.BattleContext;
import com.voidvvv.game.manager.behaviors.DamageBehavior;
import com.voidvvv.game.manager.behaviors.Behavior;
import com.voidvvv.game.utils.ReflectUtil;

public class BasePhysicAttackEvent extends AttackEvent{

    @Override
    public Behavior calculate(VActor from, VActor to) {
        VCharacter fromChar = ReflectUtil.cast(from, VCharacter.class);
        VCharacter toChar = ReflectUtil.cast(to, VCharacter.class);
        if (fromChar != null && toChar != null) {
            BattleAttr fromBattle = fromChar.getActualBattleAttr();
            BattleAttr toBattle = toChar.getActualBattleAttr();
            DamageBehavior behavior = Pools.obtain(DamageBehavior.class);
            behavior.setFrom(from);
            behavior.setTo(to);
            behavior.setTrigger(getTriggerObj().getName());
            behavior.setAttackType(BattleContext.AttackType.ATTACK_PHYSIC);
            behavior.setDamage(fromBattle.attack);
            behavior.setWorld(to.getWorld());
            behavior.fix();
            return behavior;
        }
        return null;
    }
}

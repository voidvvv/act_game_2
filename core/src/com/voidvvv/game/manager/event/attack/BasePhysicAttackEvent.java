package com.voidvvv.game.manager.event.attack;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.context.BattleContext;
import com.voidvvv.game.manager.behaviors.DamageBehavior;
import com.voidvvv.game.manager.behaviors.Behavior;
import com.voidvvv.game.manager.event.VWorldEventManager;
import com.voidvvv.game.manager.event.WorldEvent;
import com.voidvvv.game.utils.ReflectUtil;

public class BasePhysicAttackEvent extends AttackEvent  implements AttackCalculator {

    Behavior behavior = null;

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
            behavior.setAttackEvent(this);
            behavior.fix();
            return behavior;
        }
        return null;
    }

    @Override
    public boolean isEnd() {
        return this.status == WorldEvent.FINISH;
    }

    @Override
    protected boolean shouldStop() {
        return behavior == null;
    }

    @Override
    protected void spawnAndAttach() {
        behavior = calculate(fromActor, targetActor);
        // attach attack behavior to target
        targetActor.attachBehavior(behavior);
        this.status = WorldEvent.ATTACHED;
    }

    @Override
    protected boolean shouldDoPost() {
        return behavior != null && behavior.didFlag();
    }

    @Override
    public void reset() {
        super.reset();
        this.behavior = null;
    }
}

package com.voidvvv.game.context;

import com.voidvvv.game.base.VActor;
import com.voidvvv.game.manager.behaviors.DamageBehavior;

public class BaseBattleContext implements BattleContext {
    @Override
    public void init() {

    }

    @Override
    public void fixAttack(DamageBehavior behavior) {
        typeFix(behavior); //
        behavior.setFixed(true);
    }

    private void typeFix(DamageBehavior behavior) {
        VActor from = behavior.getFrom();
        VActor to = behavior.getTo();
        int attackType = behavior.getAttackType();
        float damage = behavior.getDamage();
        if (attackType == AttackType.ATTACK_PHYSIC) {
            // physic
            float defence = to.getFloat(ActorFields.DEFENCE_FIELD);
            damage = (damage * 100) / (100 + defence);
            behavior.setDamage(damage);
        }else if (attackType == AttackType.ATTACK_MAGIC){
        }
    }

    @Override
    public void update() {
        // nothing
    }

    ;
}

package com.voidvvv.game.context;

import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.manager.behaviors.DamageBehavior;

public class BaseBattleContext implements BattleContext {
    private DamageBehavior behavior;


    @Override
    public void init() {

    }

    @Override
    public void fixAttack(DamageBehavior behavior) {
        if (!behavior.isFixed()) {
            typeFix(behavior); //
            behavior.setFixed(true);
        }
    }

    private void typeFix(DamageBehavior behavior) {
        this.behavior = behavior;
        VActor from = behavior.getFrom();
        VActor to = behavior.getTo();
        int attackType = behavior.getAttackType();
        float damage = behavior.getDamage();
        if (attackType == AttackType.ATTACK_PHYSIC) {
            // physic
            float defence = to.getFloat(ActorConstants.DEFENCE_FIELD);
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

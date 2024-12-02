package com.voidvvv.game.context;

import com.voidvvv.game.base.VSkillCharacter;
import com.voidvvv.game.base.skill.Skill;
import com.voidvvv.game.manager.behaviors.DamageBehavior;

public interface BattleContext {

    boolean payForSkill(VSkillCharacter vSkillCharacter, Skill skill);

    public static class AttackType {
        public static final int ATTACK_PHYSIC = 1;
        public static final int ATTACK_MAGIC = 2;
        public static final int ATTACK_REAL = 3;
    }

    public void init ();

    public void fixAttack(DamageBehavior behavior) ;

    void update ();
}

package com.voidvvv.game.battle;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.manager.behaviors.DamageBehavior;

import java.util.HashMap;
import java.util.Map;

public class BattleComponent {
    float currentDamage;


    public final BattleAttr actualBattleAttr = new BattleAttr();


    public final BattleAttr originBattleAttr = new BattleAttr();

    public boolean battleDirty = false;

    public void addDamage(DamageBehavior damageBehavior) {
        float damage = damageBehavior.getDamage();
        int attackType = damageBehavior.getAttackType();

        currentDamage+=damage;
        damageBehavior.did = true;
        battleDirty = true;
    }
}


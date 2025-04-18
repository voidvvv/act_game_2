package com.voidvvv.game.battle;

import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.manager.behaviors.DamageBehavior;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BattleComponent {
    VCharacter owner;
    public float currentDamage;
    public float currentMagic;

    public final BattleAttr actualBattleAttr = new BattleAttr();

    public final BattleAttr transformBattleAttr = new BattleAttr();

    public final BattleAttr originBattleAttr = new BattleAttr();

    @Getter
    private BattleAttrDeltaData attackTrans = new BattleAttrDeltaData();
    @Getter
    private BattleAttrDeltaData attackSpeedTrans = new BattleAttrDeltaData();
    @Getter
    private BattleAttrDeltaData moveSpeedTrans = new BattleAttrDeltaData();


    public boolean battleDirty = true;

    public void addDamage(DamageBehavior damageBehavior) {
        float damage = damageBehavior.getDamage();
        int attackType = damageBehavior.getAttackType();

        currentDamage+=damage;

    }


    public VCharacter getOwner() {
        return owner;
    }

    public void setOwner(VCharacter owner) {
        this.owner = owner;
    }

    public void settlement() {
        if (battleDirty) {
             battleDirty = false;
            // calculate actual attribute from originBattleAttr and buff list
            if (attackTrans.dirty) {
                float attack = originBattleAttr.attack;
                attack += attackTrans.add;
                attack *= attackTrans.multi;
                actualBattleAttr.attack = attack;
            }

            if (attackSpeedTrans.dirty) {
                float attackSpeed = originBattleAttr.attackSpeed;
                attackSpeed += attackSpeedTrans.add;
                attackSpeed *= attackSpeedTrans.multi;
                actualBattleAttr.attackSpeed = attackSpeed;
            }

            if (moveSpeedTrans.dirty) {
                float moveSpeed = originBattleAttr.moveSpeed;
                moveSpeed += moveSpeedTrans.add;
                moveSpeed *= moveSpeedTrans.multi;
                actualBattleAttr.moveSpeed = moveSpeed;
            }
        }

        actualBattleAttr.hp -= currentDamage;
        currentDamage = 0f;

        actualBattleAttr.mp -= currentMagic;
        currentMagic = 0f;

        if (actualBattleAttr.hp >= actualBattleAttr.maxHp) {
            actualBattleAttr.hp = Math.max(actualBattleAttr.maxHp, 1);
        }
    }

    public void useMp(Float mpNeedCost) {
        this.currentMagic += mpNeedCost;
    }
}


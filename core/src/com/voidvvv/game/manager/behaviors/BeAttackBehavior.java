package com.voidvvv.game.manager.behaviors;

import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.battle.BattleAttr;

public class BeAttackBehavior implements Behavior {
    public static final int BASE_BE_ATTACK_BEHAVIOR = 1;

    int attackType; // magic? physic? real?
    VActor from;
    VActor to;

    VActor owner;
    float damage;
    boolean fixed =false;
    Object trigger;

    public int getAttackType() {
        return attackType;
    }

    public void setAttackType(int attackType) {
        this.attackType = attackType;
    }

    public VActor getOwner() {
        return owner;
    }

    public void setOwner(VActor owner) {
        this.owner = owner;
    }

    public VActor getFrom() {
        return from;
    }

    public void setFrom(VActor from) {
        this.from = from;
    }

    public VActor getTo() {
        return to;
    }

    public void setTo(VActor to) {
        this.to = to;
    }

    public Object getTrigger() {
        return trigger;
    }

    public void setTrigger(Object trigger) {
        this.trigger = trigger;
    }

    @Override
    public VActor owner() {
        return null;
    }

    @Override
    public void does() {
        VCharacter vch = (VCharacter) owner;
        BattleAttr actualBattle = vch.getActualBattleAttr();
        actualBattle.hp -= damage;
        vch.postBehavior(this);
    }

    @Override
    public int behaviorType() {
        return BASE_BE_ATTACK_BEHAVIOR;
    }

    @Override
    public void attach(VActor actor) {
        // bind
        this.owner = actor;

    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    @Override
    public void reset() {
        fixed = false;
        attackType = 0; // magic? physic? real?
        from = null;
        to = null;
        owner = null;
        damage = 0f;
        fixed =false;
        trigger = null;
    }
}

package com.voidvvv.game.manager.behaviors;

import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.manager.event.WorldEvent;
import com.voidvvv.game.manager.event.attack.AttackEvent;
import com.voidvvv.game.utils.ReflectUtil;

public class DamageBehavior implements Behavior {
    public static final int BASE_BE_ATTACK_BEHAVIOR = 1;

    AttackEvent attackEvent;

    VWorld world;
    int attackType; // magic? physic? real?
    VActor from;
    VActor to;

    VActor owner;
    float damage;
    boolean fixed =false;
    public boolean did = false;
    String trigger;

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

    public VWorld world() {
        return world;
    }

    public void setWorld(VWorld world) {
        this.world = world;
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

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    @Override
    public VActor owner() {
        return owner;
    }

    @Override
    public void does() {

        VCharacter vch = (VCharacter) owner;
        BattleComponent battleComponent = vch.getBattleComponent();
        battleComponent.addDamage(this);
        vch.postBehavior(this);
        VCharacter from = ReflectUtil.cast(this.from, VCharacter.class);
        if (from != null) {
            from.postBehavior(this);
        }
        world.postBehavior(this);
        this.did = true;
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

    @Override
    public boolean didFlag() {
        return this.did;
    }

    public void setAttackEvent(AttackEvent attackEvent) {
        this.attackEvent = attackEvent;
    }

    @Override
    public WorldEvent event() {
        return this.attackEvent;
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
        trigger = null;
        attackEvent = null;
        did = false;
    }

    public void fix() {
        if (!fixed) {
            world().getBattleContext().fixAttack(this);
            fixed = true;
        }
    }
}

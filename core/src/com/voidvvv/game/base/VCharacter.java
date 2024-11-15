package com.voidvvv.game.base;

import com.badlogic.gdx.ai.steer.behaviors.Jump;
import com.badlogic.gdx.math.Vector3;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.battle.Attackable;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.context.WorldContext;

import java.util.List;

/**
 * can move, can collision, can damage even attack!
 */
public class VCharacter extends VActor implements Attackable {

    BattleAttr battleAttr = new BattleAttr();

    protected Vector3 baseMove = new Vector3();

    Vector3[] velAffect = new Vector3[10];
    int velAffectCap = 0;

    private VJump vJump = new VJump();

    public VCharacter() {
        for (int x= 0; x< velAffect.length;x++) {
            velAffect[x] = new Vector3();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        fixVelocity(delta);

    }

    private void fixVelocity(float delta) {
        vJump.update(delta);
        this.velocity.set(vJump.vel).add(baseMove.nor().scl(battleAttr.moveSpeed * delta));
        for (int x=0; x<velAffectCap; x++) {
            this.velocity.add(velAffect[x]);
        }
        this.position.z += this.velocity.z * delta;
        if (this.position.z <= 0.f) {
            this.position.z = 0.f;
            vJump.reset();
        }
        this.getBody().setLinearVelocity(this.velocity.x,this.velocity.y);
    }

    @Override
    protected void updateFrameIndex() {
        if (this.currentFrame != ActGame.gameInstance().getFrameId()) {
            this.currentFrame = ActGame.gameInstance().getFrameId();
            this.velAffectCap = 0;
        }
    }

    public void jump () {
        if (isFalling()) {
            return;
        }
        vJump.jump(10f);
    }

    private boolean isFalling() {
        return this.position.z > 0.f;
    }

    public void applyExternalVel (Vector3 vel) {
        updateFrameIndex();
        if (this.velAffectCap >= this.velAffect.length) {
            return;
        }
        this.velAffect[velAffectCap++].set(vel);
    }

    @Override
    public float getHp() {
        return battleAttr.hp;
    }

    @Override
    public float getMp() {
        return battleAttr.mp;
    }

    @Override
    public float getAttack() {
        return battleAttr.attack;
    }

    @Override
    public float getDefense() {
        return battleAttr.defense;
    }

    @Override
    public float getMagicStrength() {
        return battleAttr.magicStrength;
    }

    @Override
    public float getMoveSpeed() {
        return battleAttr.moveSpeed;
    }

    @Override
    public float getAttackSpeed() {
        return battleAttr.attackSpeed;
    }

    @Override
    public float getMagicSpeed() {
        return battleAttr.magicSpeed;
    }

    protected void determineDirect () {
        // nothing
    }
}

package com.voidvvv.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.battle.Attackable;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.battle.BattleAttrDelta;
import com.voidvvv.game.context.BattleContext;
import com.voidvvv.game.context.map.VPathFinder;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.manager.behaviors.BeAttackBehavior;
import com.voidvvv.game.manager.behaviors.Behavior;

/**
 * can move, can collision, can damage even attack!
 */
public class VCharacter extends VActor implements Attackable {

    protected final BattleAttr actualBattleAttr = new BattleAttr();

    protected final BattleAttrDelta battleAttrDelta = new BattleAttrDelta();

    protected final BattleAttr originBattleAttr = new BattleAttr();

    private boolean battleDirty = false;

    public Vector3 baseMove = new Vector3();

    Vector3[] velAffect = new Vector3[10];

    private VPathFinder finder;

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
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        finder.draw(batch,parentAlpha);
    }

    @Override
    public void init() {
        super.init();
        finder = new VPathFinder(this,getWorld());
    }

    @Override
    protected void updatePosition(float delta) {
        super.updatePosition(delta);
        // update velocity
        baseMove(delta);
        this.velocity.set(vJump.vel).add(baseMove);
        for (int x=0; x<velAffectCap; x++) {
            this.velocity.add(velAffect[x]);
        }

        // update position (apply velocity)
        this.position.z += this.velocity.z * delta;
        vJump.update(delta);
        if (this.position.z <= 0.f) {
            this.position.z = 0.f;
            vJump.reset();
        }
        this.getBody().setLinearVelocity(this.velocity.x,this.velocity.y);
        finder.update(delta);
        moveFix = false;
    }

    Vector2 tmp = new Vector2();
    boolean moveFix = false;
    public Vector2 baseMove(float delta) {
        if (!moveFix) {
            moveFix = true;
            baseMove.nor().scl(actualBattleAttr.moveSpeed * delta);
            tmp.set(baseMove.x, baseMove.y);
        }
        return tmp;
    }

    public Vector2 testVelocity(float delta, Vector2 dir) {
        return tmp.set(dir).scl(actualBattleAttr.moveSpeed * delta);
    }

    public boolean findPath (float x, float y) {
        return this.finder.findPath(x,y);
    }

    @Override
    protected void updateFrameIndex() {
        if (this.currentFrame != ActGame.gameInstance().getFrameId()) {
            this.currentFrame = ActGame.gameInstance().getFrameId();
            this.velAffectCap = 0;
        }
    }

    public void jump () {
        System.out.println(this.position.z);
        if (isFalling()) {
            return;
        }
        vJump.jump(7f);
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
        return actualBattleAttr.hp;
    }

    @Override
    public float getMp() {
        return actualBattleAttr.mp;
    }

    @Override
    public float getAttack() {
        return actualBattleAttr.attack;
    }

    @Override
    public float getDefense() {
        return actualBattleAttr.defense;
    }

    @Override
    public float getMagicStrength() {
        return actualBattleAttr.magicStrength;
    }

    @Override
    public float getMoveSpeed() {
        return actualBattleAttr.moveSpeed;
    }

    @Override
    public float getAttackSpeed() {
        return actualBattleAttr.attackSpeed;
    }

    @Override
    public float getMagicSpeed() {
        return actualBattleAttr.magicSpeed;
    }


    public void useSkill(int skillCode) {

    }

    public BattleAttr getOriginBattleAttr() {
        return originBattleAttr;
    }

    public boolean isBattleDirty() {
        return battleDirty;
    }

    public BattleAttr getActualBattleAttr() {
        if (battleDirty) {
            fixBattleField();
        }
        return actualBattleAttr;
    }

    public BattleAttr getActualBattleAttrWithoutCheck() {
        return actualBattleAttr;
    }

    private void fixBattleField() {
//        BattleAttrDelta battleAttrDelta1 = this.battleAttrDelta;
//        BattleAttr originBattleAttr1 = this.originBattleAttr;

        this.battleDirty=false;
    }

    public void postBehavior(Behavior behavior) {
        SystemNotifyMessageManager systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
        systemNotifyMessageManager.pushMessage(this.getClass() + " 触发postBehavior： " + behavior.getClass());
    }

    @Override
    public float getFloat(int type) {
        if (type == BattleContext.ActorFields.DEFENCE_FIELD) {
            return this.getActualBattleAttr().defense;
        } else if (type == BattleContext.ActorFields.ATTACK_FIELD) {
            return this.getActualBattleAttr().attack;
        }
        return 0f;
    }
}

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

import java.util.*;

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
        behaviorMap = new HashMap<>();
    }


    @Override
    public void vAct(float delta) {
        super.vAct(delta);
        // fresh status

        // fresh hp and checkout damage
        Set<Map.Entry<Integer, Deque<Behavior>>> entries = behaviorMap.entrySet();
        for (Map.Entry<Integer, Deque<Behavior>> entry : entries) {
            Deque<Behavior> value = entry.getValue();
            while (value!=null && !value.isEmpty()) {
                Behavior pop = value.pop();
                pop.does();
            }
        }
    }

    protected Map<Integer,Deque<Behavior>> behaviorMap = new HashMap<>();
    @Override
    public void attachBehavior(Behavior behavior) {
        if (behavior == null) {
            return;
        }
        super.attachBehavior(behavior);
        Deque<Behavior> behaviors = behaviorMap.get(behavior.behaviorType());
        if (behaviors != null) {
            behaviors.add(behavior);
            behavior.setOwner(this);

        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        finder.draw(batch,parentAlpha);
    }

    @Override
    public void init() {
        super.init();
        finder = new VPathFinder(this,getWorld());

        behaviorMap.put(BeAttackBehavior.BASE_BE_ATTACK_BEHAVIOR,new LinkedList<>());
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

        if (behavior.behaviorType() == BeAttackBehavior.BASE_BE_ATTACK_BEHAVIOR) {
            BeAttackBehavior beAttackBehavior = (BeAttackBehavior)behavior;
            // battle type
            String msg = this.getName() + "受到了来自[" + beAttackBehavior.getFrom().getName() + "] 的 [" + (int)beAttackBehavior.getDamage() + "] 点伤害, 方式为: [" + beAttackBehavior.getTrigger() + "]  类型为: " + beAttackBehavior.getAttackType();
            systemNotifyMessageManager.pushMessage(msg);
        }
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

    @Override
    public void reset() {
        super.reset();
        behaviorMap.clear();
        finder = null;
    }
}

package com.voidvvv.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.battle.Attackable;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.context.BattleContext;
import com.voidvvv.game.context.map.VPathFinder;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.manager.behaviors.DamageBehavior;
import com.voidvvv.game.manager.behaviors.Behavior;
import com.voidvvv.game.utils.ReflectUtil;
import lombok.var;

import java.util.*;

/**
 * can move, can collision, can damage even attack!
 */
public class VCharacter extends VActor implements Attackable {

    protected final BattleComponent battleComponent = new BattleComponent();


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
        behaviorsApply(delta);
        // refresh attr
        refreshAttr(delta);
    }

    private void refreshAttr(float delta) {
        battleComponent.settlement();
    }

    protected void behaviorsApply(float delta) {
        Set<Map.Entry<Integer, Deque<Behavior>>> entries = behaviorMap.entrySet();
        for (Map.Entry<Integer, Deque<Behavior>> entry : entries) {
            Deque<Behavior> value = entry.getValue();
            while (value!=null && !value.isEmpty()) {
                Behavior pop = value.pop();
                pop.does();
                Pools.free(pop);
            }
        }
    }

    protected Map<Integer,Deque<Behavior>> behaviorMap;
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

        behaviorMap.put(DamageBehavior.BASE_BE_ATTACK_BEHAVIOR,new LinkedList<>());
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
            baseMove.nor().scl(battleComponent.actualBattleAttr.moveSpeed * delta);
            tmp.set(baseMove.x, baseMove.y);
        }
        return tmp;
    }

    public Vector2 testVelocity(float delta, Vector2 dir) {
        return tmp.set(dir).scl(battleComponent.actualBattleAttr.moveSpeed * delta);
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
        return battleComponent.actualBattleAttr.hp;
    }

    @Override
    public float getMp() {
        return battleComponent.actualBattleAttr.mp;
    }

    @Override
    public float getAttack() {
        return battleComponent.actualBattleAttr.attack;
    }

    @Override
    public float getDefense() {
        return battleComponent.actualBattleAttr.defense;
    }

    @Override
    public float getMagicStrength() {
        return battleComponent.actualBattleAttr.magicStrength;
    }

    @Override
    public float getMoveSpeed() {
        return battleComponent.actualBattleAttr.moveSpeed;
    }

    @Override
    public float getAttackSpeed() {
        return battleComponent.actualBattleAttr.attackSpeed;
    }

    @Override
    public float getMagicSpeed() {
        return battleComponent.actualBattleAttr.magicSpeed;
    }


    public void useSkill(int skillCode) {

    }

    public BattleAttr getOriginBattleAttr() {
        return battleComponent.originBattleAttr;
    }

    public boolean isBattleDirty() {
        return battleComponent.battleDirty;
    }

    public BattleAttr getActualBattleAttr() {
        if (battleComponent.battleDirty) {
            fixBattleField();
        }
        return battleComponent.actualBattleAttr;
    }

    private void fixBattleField() {
//        BattleAttrDelta battleAttrDelta1 = this.battleAttrDelta;
//        BattleAttr originBattleAttr1 = this.originBattleAttr;

        battleComponent.battleDirty=false;
    }

    public BattleComponent getBattleComponent() {
        return battleComponent;
    }

    public void postBehavior(Behavior behavior) {
        dealDamageBehavior(behavior);
    }

    protected void dealDamageBehavior(Behavior behavior) {
        if (behavior.behaviorType() == DamageBehavior.BASE_BE_ATTACK_BEHAVIOR) {
            DamageBehavior damage = ReflectUtil.cast(behavior, DamageBehavior.class);
            if (damage != null) {
                if (damage.getTo() == this) {
                    beDamaged(damage);
                }
                if (damage.getFrom() == this) {
                    afterMakeDamage(damage);
                }
            }
        }

    }

    protected void afterMakeDamage(DamageBehavior damage) {

    }

    protected void beDamaged(DamageBehavior damage) {
        SystemNotifyMessageManager systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
        String msg = this.getName() + "受到了来自[" + damage.getFrom().getName() + "] 的 [" + (int)damage.getDamage() + "] 点伤害, 方式为: [" + damage.getTrigger() + "]  类型为: " + damage.getAttackType();
        systemNotifyMessageManager.pushMessage(msg);
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
        for (var entry :behaviorMap.entrySet()) {
            Deque<Behavior> value = entry.getValue();
            Pools.free(value);
        }
        behaviorMap.clear();
        finder = null;
    }
}

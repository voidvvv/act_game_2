package com.voidvvv.game.base;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.buff.BuffComponent;
import com.voidvvv.game.battle.Attackable;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.context.BattleContext;
import com.voidvvv.game.context.map.VPathFinder;
import com.voidvvv.game.manager.FontManager;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.manager.behaviors.DamageBehavior;
import com.voidvvv.game.manager.behaviors.Behavior;
import com.voidvvv.game.manager.event.attack.AttackEvent;
import com.voidvvv.game.screen.test.ui.Box2dUnitConverter;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.*;

/**
 * can move, can collision, can damage even attack!
 */
public class VCharacter extends VActor implements Attackable {

    protected final BattleComponent battleComponent = new BattleComponent();
    protected final BuffComponent buffComponent = new BuffComponent();
    protected final VActorListenerComponent listenerComponent = new VActorListenerComponent();

    public float statusTime;

    public Vector3 baseMove = new Vector3();

    public boolean flip = false;

    Vector3[] velAffect = new Vector3[10];

    private VPathFinder finder;

    int velAffectCap = 0;

    private VJump vJump = new VJump();

    protected boolean dying = false;


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
        updateBuffs(delta);
        // fresh hp and checkout damage
        behaviorsApply(delta);
        otherApply(delta);
        // refresh attr
        refreshAttr(delta);
        vCAct(delta);
        synchSpeedToBox2d();
    }

    private void updateBuffs(float delta) {
        this.buffComponent.update();
    }

    protected void otherApply(float delta) {
        listenerComponent.update();
    }

    protected void vCAct(float delta) {
    }


    protected void synchSpeedToBox2d() {
        this.getBody().setLinearVelocity(Box2dUnitConverter.worldToBox2d(this.velocity.x),Box2dUnitConverter.worldToBox2d(this.velocity.y));
    }

    private void refreshAttr(float delta) {
        battleComponent.settlement();
        if (toDying()) {
            becomeDying();
            afterBecomeDying();
        }
    }

    protected void afterBecomeDying() {
        for (VActorListener listener : listenerComponent.listeners) {
            listener.afterBecomeDying();
        }
    }

    protected void becomeDying() {
    }

    protected boolean toDying() {
        return battleComponent.actualBattleAttr.hp <= 0;
    }

    protected void behaviorsApply(float delta) {
        Set<Map.Entry<Integer, Deque<Behavior>>> entries = behaviorMap.entrySet();
        for (Map.Entry<Integer, Deque<Behavior>> entry : entries) {
            Deque<Behavior> value = entry.getValue();
            while (value!=null && !value.isEmpty()) {
                Behavior pop = value.pop();
                pop.does();
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
        FontManager fontManager = ActGame.gameInstance().getFontManager();
        BitmapFont baseFont = fontManager.getBaseFont();
        baseFont.draw(batch, getName(), position.x, position.y);

        batch.end();
        ShapeRenderer shapeRenderer = ActGame.gameInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.setProjectionMatrix(getStage().getCamera().combined);
        shapeRenderer.begin();
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
        batch.begin();
    }

    @Override
    public void init() {
        super.init();
        this.battleComponent.setOwner(this);
        this.buffComponent.setOwner(this);
        finder = new VPathFinder(this,getWorld());

        behaviorMap.put(DamageBehavior.BASE_BE_ATTACK_BEHAVIOR,new LinkedList<>());
    }

    public BuffComponent getBuffComponent() {
        return buffComponent;
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
        vJump.update(delta);
        if (this.position.z <= 0.f) {
            this.position.z = 0.f;
            vJump.reset();
        }
        finder.update(delta);
        moveFix = false;
    }

    public Vector2 tmp = new Vector2();
    boolean moveFix = false;
    public Vector2 baseMove(float delta) {
        if (!moveFix) {
            moveFix = true;
            baseMove.nor().scl(battleComponent.actualBattleAttr.moveSpeed);
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
        if (isFalling()) {
            return;
        }
        vJump.jump(7f);
    }

    private boolean isFalling() {
        return this.position.z > 0.f;
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
        battleComponent.settlement();
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
                    afterBeDamaged(damage);
                }
                if (damage.getFrom() == this) {
                    afterMakeDamage(damage);
                }
            }
        }
    }

    public DamageBehavior lastMakedDamageBehavior;
    protected void afterMakeDamage(DamageBehavior damage) {
        lastMakedDamageBehavior = damage;
        for (VActorListener initListener : listenerComponent.listeners) {
            initListener.afterMakeDamage();
        }
        lastMakedDamageBehavior = null;
    }

    public DamageBehavior lastBeDamagedBehavior;
    protected void afterBeDamaged(DamageBehavior damage) {
//        SystemNotifyMessageManager systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
//        String msg = this.getName() + "受到了来自[" + damage.getFrom().getName() + "] 的 [" + (int)damage.getDamage() + "] 点伤害, 方式为: [" + damage.getTrigger() + "]  类型为: " + damage.getAttackType() + "  当前生命值: " + getBattleComponent().actualBattleAttr.hp;
//        systemNotifyMessageManager.pushMessage(msg);
        lastBeDamagedBehavior = damage;
        for (VActorListener initListener : listenerComponent.listeners) {
            initListener.afterBeDamage();
        }
        lastBeDamagedBehavior = null;
    }

    @Override
    public float getFloat(int type) {
        if (type == ActorConstants.DEFENCE_FIELD) {
            return this.getActualBattleAttr().defense;
        } else if (type == ActorConstants.ATTACK_FIELD) {
            return this.getActualBattleAttr().attack;
        }
        return 0f;
    }

    @Override
    public void reset() {
        super.reset();
        for (Map.Entry<Integer, Deque<Behavior>> entry :behaviorMap.entrySet()) {
            Deque<Behavior> value = entry.getValue();
            for (Behavior b: value) {
                Pools.free(b);
            }
            value.clear();
        }
        behaviorMap.clear();
        finder = null;
    }

    public void interruptPathFinding() {
        this.finder.interrupt();
    }

    @Override
    public boolean isDying() {
        return dying;
    }
    @Override
    public void setDying(boolean dying) {
        this.dying = dying;
    }

    /**
     * if this VActor could contact with another
     * @param another
     * @return
     */
    @Override
    public boolean couldContact(VActor another) {
        return !this.isDying() && !another.isDying();
    }

    public VActorListenerComponent getListenerComponent() {
        return listenerComponent;
    }

    public AttackEvent lastMadeAttack;
    @Override
    public void postAttack(AttackEvent attackEvent) {
        lastMadeAttack = attackEvent;
        for (VActorListener initListener : listenerComponent.listeners) {
            initListener.afterMakeAttackEvent();
        }
        lastMadeAttack = null;
    }
    public AttackEvent lastBeAttackedEvent;

    @Override
    public void postBeAttacked(AttackEvent attackEvent) {
        lastBeAttackedEvent = attackEvent;
        for (VActorListener initListener : listenerComponent.listeners) {
            initListener.afterBeAttackEvent();
        }
        lastBeAttackedEvent = null;
    }

    public VActor lastHitActor;
    @Override
    public void onHit(VActor actor) {
        lastHitActor = actor;
        super.onHit(actor);
        for (VActorListener listener : listenerComponent.listeners) {
            listener.afterHitOnActor();
        }
        lastHitActor = null;
    }
}

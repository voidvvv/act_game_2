package com.voidvvv.game.base;

import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.actors.NormalDetector;
import com.voidvvv.game.base.buff.Buff;
import com.voidvvv.game.base.buff.BuffComponent;
import com.voidvvv.game.base.skill.v2.Skill;
import com.voidvvv.game.base.state.StatusData;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.battle.Attackable;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.battle.BattleComponent;
import com.voidvvv.game.context.map.VPathFinder;
import com.voidvvv.game.manager.FontManager;
import com.voidvvv.game.manager.behaviors.Behavior;
import com.voidvvv.game.manager.behaviors.DamageBehavior;
import com.voidvvv.game.manager.event.attack.AttackEvent;
import com.voidvvv.game.plugin.PluginComponent;
import com.voidvvv.game.screen.test.ui.Box2dUnitConverter;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * can move, can collision, can damage even attack!
 */
public class VCharacter extends VActor implements Attackable {

    protected final BattleComponent battleComponent = new BattleComponent();
    protected final BuffComponent buffComponent = new BuffComponent();
    protected final VActorListenerComponent listenerComponent = new VActorListenerComponent();
    protected final PluginComponent pluginComponent = new PluginComponent();


    public float statusTime;
    public float statusProgress;

    public Vector3 baseMove = new Vector3();

    private NormalDetector normalDetector;


    Vector3[] velAffect = new Vector3[10];

    private VPathFinder finder;

    int velAffectCap = 0;

    private VJump vJump = new VJump();

    protected boolean dying = false;


    public VCharacter() {
        for (int x = 0; x < velAffect.length; x++) {
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
        pluginComponent.update(delta);
    }

    protected void vCAct(float delta) {
    }


    protected void synchSpeedToBox2d() {
        this.getBody().setLinearVelocity(Box2dUnitConverter.worldToBox2d(this.velocity.x), Box2dUnitConverter.worldToBox2d(this.velocity.y));
        if (this.velocity.len() > 0.1f) {
            postMove(this.velocity);
        }
    }

    public Vector2 lastMoveVel = new Vector2();

    public void postMove(Vector3 velocity) {
        lastMoveVel.set(velocity.x, velocity.y);
        for (VActorListener listener : this.listenerComponent.listeners) {
            listener.afterMove();
        }
        lastMoveVel.set(0f, 0f);

    }

    private void refreshAttr(float delta) {
        battleComponent.settlement();
        refreshVelocity(delta);
        if (toDying()) {
            becomeDying();
            afterBecomeDying();
        }
    }

    private void refreshVelocity(float delta) {
        // update velocity
        baseMove(delta);
        this.velocity.set(vJump.vel).add(baseMove);
        for (int x = 0; x < velAffectCap; x++) {
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

    protected void afterBecomeDying() {
        for (VActorListener listener : listenerComponent.listeners) {
            listener.afterBecomeDying();
        }
    }

    protected void becomeDying() {
        this.setDying(true);
        this.pluginComponent.reset();
        MessageManager.getInstance().dispatchMessage(this,getStateMachine(), ActorConstants.MSG_ACTOR_AFTER_DYING);
    }

    protected boolean toDying() {
        return !isDying() && battleComponent.actualBattleAttr.hp <= 0;
    }

    protected void behaviorsApply(float delta) {
        Set<Map.Entry<Integer, Deque<Behavior>>> entries = behaviorMap.entrySet();
        for (Map.Entry<Integer, Deque<Behavior>> entry : entries) {
            Deque<Behavior> value = entry.getValue();
            while (value != null && !value.isEmpty()) {
                Behavior pop = value.pop();
                pop.does();
            }
        }
    }

    protected Map<Integer, Deque<Behavior>> behaviorMap;

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
        finder.draw(batch, parentAlpha);
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
        this.setDying( false);
        this.battleComponent.setOwner(this);
        this.buffComponent.setOwner(this);
        finder = new VPathFinder(this, getWorld());

        behaviorMap.put(DamageBehavior.BASE_BE_ATTACK_BEHAVIOR, new LinkedList<>());
    }

    boolean aiInit = false;
    public void initAI(float radius) {
        if (!aiInit) {
            aiInit = true;
            normalDetector = Pools.obtain(NormalDetector.class);
            normalDetector.init(this);
            normalDetector.changeRadius(radius);

        } else {
            normalDetector.changeRadius(radius);
        }
    }

    public BuffComponent getBuffComponent() {
        return buffComponent;
    }


    public Vector2 tmp = new Vector2();
    boolean moveFix = false;

    public void baseMove(float delta) {
        this.baseMove.nor();
        preVelocity.nor();
        tmp.nor();
        float preX = preVelocity.x;
        float preY = preVelocity.y;
        preVelocity.x = this.baseMove.x;
        preVelocity.y = this.baseMove.y;

        baseMove.x = tmp.x;
        baseMove.y = tmp.y;
        if (MathUtils.isEqual(preVelocity.x, baseMove.x) && MathUtils.isEqual(preVelocity.y, baseMove.y)) {
            if (!moveFix) {
                moveFix = true;
                baseMove.nor().scl(battleComponent.actualBattleAttr.moveSpeed);
            }
            return;
        }

        sd.setSuccess(true); // expect true!
        MessageManager.getInstance().dispatchMessage(this, getStateMachine(), ActorConstants.MSG_ACTOR_BASE_VELOCITY_CHANGE, sd, true);
        if (!sd.isSuccess()) {

            this.baseMove.x = preVelocity.x;
            this.baseMove.y = preVelocity.y;
            this.preVelocity.x = preX;
            this.preVelocity.y = preY;
        }
        if (!moveFix) {
            moveFix = true;
            baseMove.nor().scl(battleComponent.actualBattleAttr.moveSpeed);
        }
    }

    public Vector2 testVelocity(float delta, Vector2 dir) {
        return dir.nor().scl(battleComponent.actualBattleAttr.moveSpeed * delta);
    }

    public boolean findPath(float x, float y) {
        return this.finder.findPath(x, y);
    }

    @Override
    protected void updateFrameIndex() {
        if (this.currentFrame != ActGame.gameInstance().getFrameId()) {
            this.currentFrame = ActGame.gameInstance().getFrameId();
            this.velAffectCap = 0;
        }
    }

    public void jump() {
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
        MessageManager.getInstance().dispatchMessage(this, getStateMachine(), ActorConstants.MSG_ACTOR_AFTER_BE_DAMAGED, sd);
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
        aiInit = false;
        if (normalDetector != null) {

            Pools.free(normalDetector);
            normalDetector = null;
        }
        for (Map.Entry<Integer, Deque<Behavior>> entry : behaviorMap.entrySet()) {
            Deque<Behavior> value = entry.getValue();
            for (Behavior b : value) {
                Pools.free(b);
            }
            value.clear();
        }
        getPluginComponent().reset();
        behaviorMap.clear();
        this.listenerComponent.reset();
        finder = null;

        super.reset();
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
     *
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

    public PluginComponent getPluginComponent() {
        return pluginComponent;
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
        MessageManager.getInstance().dispatchMessage(this, getStateMachine(), ActorConstants.MSG_ACTOR_AFTER_BE_ATTACK, sd);

        lastBeAttackedEvent = attackEvent;
        for (VActorListener initListener : listenerComponent.listeners) {
            initListener.afterBeAttackEvent();
        }
        lastBeAttackedEvent = null;
    }

    public com.voidvvv.game.base.skill.v2.Skill lastUsedSkill;

    @Override
    public void postUseSkill(com.voidvvv.game.base.skill.v2.Skill skill) {
        lastUsedSkill = skill;
        for (VActorListener listener : listenerComponent.listeners) {
            listener.afterUseSkill();
        }
        lastUsedSkill = null;
    }

    public VActor lastHitActor;
    public Fixture lastThisFixture;
    public Fixture lastOtherFixture;

    @Override
    public void onHit(VActor actor, Fixture thisFixture, Fixture otherFixture) {
        lastHitActor = actor;
        lastThisFixture = thisFixture;
        lastOtherFixture = otherFixture;
        super.onHit(actor, thisFixture, otherFixture);
        for (VActorListener listener : listenerComponent.listeners) {
            listener.afterHitOnActor();
        }
        lastThisFixture = null;
        lastOtherFixture = null;
        lastHitActor = null;
    }

    public void onHitOver(VActor actor, Fixture thisFixture, Fixture otherFixture) {
        lastHitActor = actor;
        lastThisFixture = thisFixture;
        lastOtherFixture = otherFixture;
        super.onHit(actor, thisFixture, otherFixture);
        for (VActorListener listener : listenerComponent.listeners) {
            listener.afterHitOver();
        }
        lastThisFixture = null;
        lastOtherFixture = null;
        lastHitActor = null;
    }

    public Buff lastAddedAbuff;

    @Override
    public void postAddBuff(Buff buff) {
        lastAddedAbuff = buff;
        for (VActorListener listener : listenerComponent.listeners) {
            listener.afterAddBuff();
        }
        lastAddedAbuff = null;
    }

    public void setFrameSkill(int keycode) {

    }

    public Skill lastPreSkill;
    public float currentSkillSpellingTime;

    public NormalDetector getNormalDetector() {
        return normalDetector;
    }

    public void execStatus(VCharactorStatus status) {
        status.exec(this);
    }

    public Vector3 preVelocity = new Vector3();

    public StatusData sd = new StatusData();
    public void setHorizonVelocity(float x, float y) {
        tmp.set(x,y).nor();

    }

    public void setHorizontalX (float x) {
        setHorizonVelocity(x, this.tmp.y);

    }

    public void setHorizontalY (float y) {
        setHorizonVelocity(this.tmp.x, y);
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }

    public StateMachine<VCharacter, VCharactorStatus> getStateMachine() {
        return null;
    }

    // change status or not
    public boolean preUseSkill(Skill lightBoomSkill) {
        lastPreSkill = lightBoomSkill;
        currentSkillSpellingTime = lightBoomSkill.spellingTime;
        sd.setSuccess(true); // expect true!
        MessageManager.getInstance().dispatchMessage(this, this.getStateMachine(), ActorConstants.MSG_ACTOR_PRE_ATTEMPT_TO_SPELL, sd, true);

        if (sd.isSuccess()){
            for (VActorListener listener : listenerComponent.listeners) {
                listener.preUserSkill();
            }
        }

        lastPreSkill = null;
        currentSkillSpellingTime = 0f;
        return sd.isSuccess();
    }
}

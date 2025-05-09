package com.voidvvv.game.base;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.buff.Buff;
import com.voidvvv.game.manager.behaviors.Behavior;
import com.voidvvv.game.context.world.VWorld;
import com.voidvvv.game.manager.event.attack.AttackEvent;
import com.voidvvv.game.screen.test.ui.Box2dUnitConverter;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

public abstract class VActor extends Actor implements Pool.Poolable, Telegraph {
    protected int version = 0;

    private VWorld world;

    public long currentFrame = 0;

    public float liveTime = 0f;

    public final Vector3 velocity = new Vector3();

    public final Vector3 position = new Vector3();

    public VPhysicAttr physicAttr;

    public int actorType;

    private Fixture mainFixture;
    @Getter
    @Setter
    private Fixture collideFixture;

    private Body body;

    public boolean flipX = false;
    public boolean flipY = false;

    public final Camp camp = new Camp();
    public final Camp taregtCamp = new Camp();

    boolean vActive = true;

    public boolean isvActive() {
        return vActive;
    }

    public void setvActive(boolean vActive) {
        this.vActive = vActive;
    }

    protected VActor parentVActor;

    public VActor getParentVActor() {
        return parentVActor;
    }

    public void setParentVActor(VActor parentVActor) {
        this.parentVActor = parentVActor;
    }

    public VPhysicAttr getPhysicAttr() {
        return physicAttr;
    }

    public void setPhysicAttr(VPhysicAttr physicAttr) {
        this.physicAttr = physicAttr;
    }

    public Fixture getMainFixture() {
        return mainFixture;
    }

    public Body getBody() {
        return body;
    }

    public void init() {
        this.currentFrame = ActGame.gameInstance().getFrameId() - 1;
        setvActive(true);
    }

    public void setMainFixture(Fixture mainFixture) {
        this.mainFixture = mainFixture;
        this.body = mainFixture.getBody();
    }

    public void act(float delta) {
        if (vActive) {
            liveTime+=delta;
            vAct(delta);
        }
    }

    public void vAct(float delta) {
        updateFrameIndex();
        updatePosition(delta);
        updateSize(delta);

    }


    protected Transform transform = null;
    protected void updateSize(float delta) {
        setWidth(this.physicAttr.getBaseShape().getBounds().x);
        setHeight(this.physicAttr.getBaseShape().getHeight());
        setX(position.x - getWidth() / 2);
        setY(position.y + position.z);
        setRotation(transform.getRotation());
    }

    protected void updatePosition(float delta) {
        transform = body.getTransform();
        Vector2 position1 = transform.getPosition();
        Box2dUnitConverter.box2dToWorld(position1);
        this.position.x = position1.x;
        this.position.y = position1.y - getPhysicAttr().box2dHy;
        this.position.z += this.velocity.z * delta;

    }

    public void update (float delta) {
        act(delta);
    }

    protected void updateFrameIndex() {
        if (this.currentFrame != ActGame.gameInstance().getFrameId()) {
            this.currentFrame = ActGame.gameInstance().getFrameId();
        }
    }


    @Override
    public void reset() {
        this.version++;
        this.getWorld().getBox2dWorld().destroyBody(body);
        this.setWorld(null);
        this.setParentVActor(null);
        liveTime = 0;
        MessageManager.getInstance().dispatchMessage(ActorConstants.MSG_RESET_ACTOR, this);
    }

    public boolean shoulCollide(VActor actor) {
        return true;
    }

    public void postAttack(AttackEvent attackEvent) {
    }


    public void postBeAttacked(AttackEvent attackEvent) {
    }

    public void postUseSkill (com.voidvvv.game.base.skill.v2.Skill skill) {

    }

    public abstract void postAddBuff(Buff buff);

    public void changeStatus(int statusIdle) {

    }

    public int currentStateId() {
        return 0;
    }

    public void onHitOver(VActor actor, Fixture fixtureA, Fixture fixtureB) {

    }


    public static class VActorCompare implements Comparator<VActor> {

        @Override
        public int compare(VActor o1, VActor o2) {
            return (o1.position.y - o2.position.y) > 0 ? 1 : 0;
        }
    }

    public VWorld getWorld() {
        return world;
    }

    public void setWorld(VWorld world) {
        this.world = world;
    }

    public void attachBehavior(Behavior behavior){

    }

    public float getFloat(int type) {
        return 0;
    }

    public void setFloat(int type, float value) {

    }


    public void onHit(VActor actor, Fixture thisFixture, Fixture otherFixture) {
    }

    public boolean isDying() {
        return false;
    };

    public void setDying(boolean dying){};

    public boolean couldContact(VActor actor) {
        return true;
    }

    public int getVersion() {
        return version;
    }
}

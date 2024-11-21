package com.voidvvv.game.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.manager.behaviors.Behavior;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.Comparator;

public class VActor extends Actor implements Pool.Poolable {
    private VWorld world;

    public long currentFrame = 0;

    public final Vector3 velocity = new Vector3();

    public final Vector3 position = new Vector3();

    protected VPhysicAttr physicAttr;

    private Fixture fixture;

    private Body body;

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

    public Fixture getFixture() {
        return fixture;
    }

    public Body getBody() {
        return body;
    }

    public void init() {
        this.currentFrame = ActGame.gameInstance().getFrameId() - 1;
        setvActive(true);
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
        this.body = fixture.getBody();
    }

    public void act(float delta) {
        if (vActive) {
            vAct(delta);
        }
    }

    public void vAct(float delta) {
        updateFrameIndex();
        updatePosition(delta);
        updateSize(delta);

    }

    Transform transform = null;
    private void updateSize(float delta) {
        setWidth(this.physicAttr.getBaseShape().getBounds().x);
        setHeight(this.physicAttr.getBaseShape().getHeight());
        setX(position.x - getWidth() / 2);
        setY(position.y + position.z);
        setRotation(transform.getRotation());
    }

    protected void updatePosition(float delta) {
        transform = body.getTransform();
        Vector2 position1 = transform.getPosition();
        UserData cast = ReflectUtil.cast(fixture.getUserData(), UserData.class);
        this.position.x = position1.x;
        this.position.y = position1.y;
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
        this.getWorld().getBox2dWorld().destroyBody(body);
        this.setWorld(null);
        this.setParentVActor(null);
    }

    public boolean shoulCollide(VActor actor) {
        return true;
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

    public void onHit(VActor actor) {
        SystemNotifyMessageManager systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();

        systemNotifyMessageManager.pushMessage(this.getName() + " hit with: " + actor.getName());
    }
}

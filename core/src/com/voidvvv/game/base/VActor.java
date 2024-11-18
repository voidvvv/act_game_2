package com.voidvvv.game.base;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.Comparator;

public class VActor extends Actor {
    private VWorld world;

    public long currentFrame = 0;

    public final Vector3 velocity = new Vector3();

    public final Vector3 position = new Vector3();

    protected VPhysicAttr physicAttr;

    private Fixture fixture;

    private Body body;

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
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
        this.body = fixture.getBody();
    }

    public void act(float delta) {
        updateFrameIndex();
        updatePosition(delta);
        updateSize(delta);
    }

    private void updateSize(float delta) {
        setWidth(this.physicAttr.getBaseShape().getBounds().x);
        setHeight(this.physicAttr.getBaseShape().getBounds().y);
        setX(position.x - getWidth() / 2);
        setY(position.y + position.z);
    }

    protected void updatePosition(float delta) {

        Vector2 position1 = body.getPosition();
        UserData cast = ReflectUtil.cast(fixture.getUserData(), UserData.class);
        this.position.x = position1.x;
        this.position.y = position1.y + cast.getSubShifting();
    }

    public void update (float delta) {
        act(delta);
    }

    protected void updateFrameIndex() {
        if (this.currentFrame != ActGame.gameInstance().getFrameId()) {
            this.currentFrame = ActGame.gameInstance().getFrameId();
        }
    }


    public void render() {
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
}

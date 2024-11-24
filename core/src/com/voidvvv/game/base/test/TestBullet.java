package com.voidvvv.game.base.test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.manager.event.attack.AttackEvent;
import com.voidvvv.game.manager.event.attack.BasePhysicAttackEvent;
import com.voidvvv.game.render.actor.test.bullet.TestBulletRender;
import com.voidvvv.game.utils.ReflectUtil;

public class TestBullet extends VCharacter {

    public float maxLive = 10f;
    public float currentTime = 0f;

    public short targetGroup = 0;

    public static final int NORMAL = 0;
    public static final int HIT = 1;
    public static final int DYING = 2;


    int staus = 0;

    VCharacter target = null;

    Class<? extends AttackEvent>  typeClazz = BasePhysicAttackEvent.class;

    TestBulletRender testBulletRender;

    @Override
    public void vAct(float delta) {
        super.vAct(delta);
        currentTime -= delta;
        if (currentTime <= 0) {
            this.staus = DYING;
        }
        if (staus == HIT && target != null) {
            AttackEvent attackEvent = ActGame.gameInstance().getvWorldEventManager().newEvent(typeClazz);
            attackEvent.setFromActor(getParentVActor());
            attackEvent.setTargetActor(target);
            attackEvent.setTriggerObj(this);
            attackEvent.setExtraInfo(this);
            this.staus = DYING;
        } else if (staus == DYING) {
            getWorld().destroyActor(this);

        }
    }

    public Class<? extends AttackEvent> getTypeClazz() {
        return typeClazz;
    }

    public void setTypeClazz(Class<? extends AttackEvent> typeClazz) {
        this.typeClazz = typeClazz;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        testBulletRender.render(this, batch, parentAlpha);
    }

    @Override
    public void init() {
        super.init();
        VCube cube = new VCube();
        cube.xLength = this.physicAttr.box2dHx * 2f;
        cube.yLength = this.physicAttr.box2dHy * 2f;
        cube.zLength = 10f;
        physicAttr.setBaseShape(cube);
        currentTime = maxLive;
        setName("闪光炸弹");

        testBulletRender = new TestBulletRender();
        testBulletRender.init();
    }

    @Override
    public void reset() {
        super.reset();
        this.staus = NORMAL;
    }

    @Override
    public void onHit(VActor actor) {
        super.onHit(actor);
        if (actor == null) {
            return;
        }
        if (VObstacle.class.isAssignableFrom(actor.getClass())) {
            staus = DYING;
            return;
        }
        VCharacter character = ReflectUtil.cast(actor,VCharacter.class);
        Fixture fixture = actor.getFixture();
        short categoryBits = fixture.getFilterData().categoryBits;
        boolean isTarget = (categoryBits&targetGroup) == targetGroup;
        if (isTarget && character != null) {
            // enemy target character
            target = character;
            staus = HIT;
        }
    }



    @Override
    public boolean shoulCollide(VActor actor) {
        return false;
    }
}

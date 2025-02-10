package com.voidvvv.game.base.test;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.manager.event.attack.AttackEvent;
import com.voidvvv.game.manager.event.attack.BasePhysicAttackEvent;
import com.voidvvv.game.render.actor.test.bullet.TestBulletRender;
import com.voidvvv.game.utils.ReflectUtil;

public class TestBullet extends VCharacter {

    public float maxLive = 0.5f;
    public float currentTime = 0f;

    public short targetGroup = 0;

    public static final int NORMAL = 0;
    public static final int HIT = 1;
    public static final int DYING = 2;


    int staus = 0;

    VCharacter target = null;

    Class<? extends AttackEvent>  typeClazz = BasePhysicAttackEvent.class;

    static TestBulletRender testBulletRender;


    @Override
    public void vAct(float delta) {
        super.vAct(delta);
        this.actorType = ActorConstants.ACTOR_TYPE_BULLET;

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
        this.staus = NORMAL;
        currentTime = maxLive;
        setName("闪光炸弹");
        getBattleComponent().actualBattleAttr.hp = 100;
        if (testBulletRender == null) {
            testBulletRender = new TestBulletRender();
            testBulletRender.init();
        }

    }




    @Override
    public void reset() {
        super.reset();
        this.staus = NORMAL;
    }

    @Override
    public void onHit(VActor actor, Fixture thisFixture, Fixture otherFixture) {
        super.onHit(actor, thisFixture, otherFixture);

        if (actor == null || !this.couldContact(actor) || this.staus != NORMAL ) {
            return;
        }
        if (VObstacle.class.isAssignableFrom(actor.getClass())) {
            staus = DYING;
            return;
        }
        UserData ud = ReflectUtil.cast(otherFixture.getUserData(), UserData.class);
        if(ud == null || ud.isDerivative() || ud.getType() == UserData.B2DType.SENSOR) {
            return;
        }
        VCharacter character = ReflectUtil.cast(actor,VCharacter.class);
        Fixture fixture = actor.getMainFixture();
        short categoryBits = fixture.getFilterData().categoryBits;
        if (character != null && this.taregtCamp.compatible(character.camp)) {
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

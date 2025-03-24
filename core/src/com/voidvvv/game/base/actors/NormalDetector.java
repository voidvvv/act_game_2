package com.voidvvv.game.base.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.Camp;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VActorAdaptor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.context.world.WorldContext;
import com.voidvvv.game.screen.test.ui.Box2dUnitConverter;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.HashSet;
import java.util.Set;

public class NormalDetector extends VActorAdaptor implements Pool.Poolable {
    public Fixture detectFixture;
    int recordActorVersion;
    VCharacter character;
    public Set<VCharacter> characters;

    public float radius;

    int targetVersion;
    VCharacter target;

    public Set<VCharacter> getCharacters() {
        return characters;
    }

    public void setTarget(VCharacter vc) {
        this.target = vc;
        this.targetVersion = vc.getVersion();
    }

    public VCharacter getTarget() {
        return target;
    }

    public void changeRadius(float radius) {
        float oldRadius = this.radius;
        if (oldRadius == radius || oldRadius <= 0) {
            return;
        }
        this.radius = radius;
        if (detectFixture != null) {
            character.getBody().destroyFixture(detectFixture);



        }
        generateCircleRange();
    }

    private void generateCircleRange() {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Box2dUnitConverter.worldToBox2d(radius));
        circleShape.setPosition(tmp.set(0, Box2dUnitConverter.worldToBox2d(character.physicAttr.box2dHz - character.physicAttr.box2dHy)));

        FixtureDef fd = new FixtureDef();
        fd.shape = circleShape;
        fd.density = 1.0f;
        fd.friction = 0.0f;
        fd.restitution = 0.0f;
        fd.isSensor = true;
        fd.filter.categoryBits = WorldContext.FACE_COLLIDE;
        fd.filter.maskBits = WorldContext.FACE_COLLIDE;

        detectFixture = character.getBody().createFixture(fd);
        circleShape.dispose();
        UserData ud = new UserData();
        ud.setDerivative(true);
        ud.setType(UserData.B2DType.SENSOR);
        ud.setActor(character);
        detectFixture.setUserData(ud);

    }

    Vector2 tmp = new Vector2();

    public void init(VCharacter character) {
        characters = new HashSet<>();
        this.recordActorVersion = character.getVersion();
        this.character = character;
        if (radius <= 0) {
            radius = Vector2.len(character.physicAttr.box2dHx, character.physicAttr.box2dHz);
        }

        generateCircleRange();
        character.getListenerComponent().addAndFlush(this);

    }

    @Override
    public void reset() {
        super.reset();
        if (character != null && character.isvActive()) {
            if (detectFixture != null) {
                character.getBody().destroyFixture(detectFixture);
                detectFixture = null;
            }
            character = null;
        }
        target = null;
        characters.clear();
        character = null;

        characters = null;
    }

    public void removeActor(VCharacter cast) {
        boolean remove = characters.remove(cast);
        if (cast == target) {
            target = null;
        }
    }


    @Override
    public void afterHitOnActor() {
        VActor lastHitActor = character.lastHitActor;
        Fixture lastThisFixture = character.lastThisFixture;
        Fixture lastOtherFixture = character.lastOtherFixture;
        if (lastThisFixture != this.detectFixture) {
            return;
        }
        UserData ud = ReflectUtil.cast(lastOtherFixture.getUserData(), UserData.class);
        if (ud == null || ud.isDerivative() || ud.getType() == UserData.B2DType.SENSOR) {
            return;
        }
        Camp camp = lastHitActor.camp;
        if (!character.taregtCamp.compatible(camp)) {
            return;
        }
        VCharacter other = ReflectUtil.cast(lastHitActor, VCharacter.class);
        if (this.characters != null && other != null && lastOtherFixture == other.getMainFixture()) {
            this.characters.add(other);
            System.out.println("detect intruder!");
        }

    }

    @Override
    public void afterHitOver() {
        VActor lastHitActor = character.lastHitActor;
        Fixture lastThisFixture = character.lastThisFixture;
        Fixture lastOtherFixture = character.lastOtherFixture;

        if (lastThisFixture != this.detectFixture) {
            return;
        }
        if (lastOtherFixture != lastHitActor.getMainFixture()) {
            return;
        }
        Camp camp = lastHitActor.camp;
        this.removeActor(ReflectUtil.cast(lastHitActor, VCharacter.class));
    }
}

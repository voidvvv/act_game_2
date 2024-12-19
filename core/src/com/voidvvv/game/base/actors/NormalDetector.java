package com.voidvvv.game.base.actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.base.btree.DetectorListener;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.screen.test.ui.Box2dUnitConverter;

import java.util.HashSet;
import java.util.Set;

public class NormalDetector implements Pool.Poolable {
    Fixture detectFixture;
    DetectorListener listener;
    int recordActorVersion;
    VCharacter character;
    private Set<VCharacter> characters = new HashSet<>();

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

            generateCircleRange();

        }
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
        listener.fixture = detectFixture;

    }

    Vector2 tmp = new Vector2();

    public void init(VCharacter character) {
        this.recordActorVersion = character.getVersion();
        this.character = character;
        if (radius <= 0) {
            radius = Vector2.len(character.physicAttr.box2dHx, character.physicAttr.box2dHz);
        }
        DetectorListener dl = Pools.obtain(DetectorListener.class);
        listener = dl;
        generateCircleRange();


        dl.character = character;
        dl.fixture = detectFixture;
        dl.characters = characters;
        character.getListenerComponent().add(dl);

    }

    @Override
    public void reset() {
        if (character != null && character.getVersion() == recordActorVersion) {
            if (detectFixture != null) {
                character.getBody().destroyFixture(detectFixture);
                detectFixture = null;
            }
            character = null;
        }
        target = null;
        listener = null;
        characters.clear();
    }
}

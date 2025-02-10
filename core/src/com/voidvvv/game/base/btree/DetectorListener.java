package com.voidvvv.game.base.btree;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.base.Camp;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VActorAdaptor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.actors.NormalDetector;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.Set;

public class DetectorListener extends VActorAdaptor {
    public VCharacter character;
    public NormalDetector detector;
    @Override
    public void reset() {
        super.reset();
        character = null;
        detector = null;
    }

    @Override
    public void afterHitOnActor() {
        VActor lastHitActor = character.lastHitActor;
        Fixture lastThisFixture = character.lastThisFixture;
        Fixture lastOtherFixture = character.lastOtherFixture;
        if (lastThisFixture != detector.detectFixture) {
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
        if (detector.characters != null && other != null && lastOtherFixture == other.getMainFixture()) {
            detector.characters.add(other);
            System.out.println("detect intruder!");
        }

    }

    @Override
    public void afterHitOver() {
        VActor lastHitActor = character.lastHitActor;
        Fixture lastThisFixture = character.lastThisFixture;
        Fixture lastOtherFixture = character.lastOtherFixture;

        if (lastThisFixture != detector.detectFixture) {
            return;
        }
        if (lastOtherFixture != lastHitActor.getMainFixture()) {
            return;
        }
        Camp camp = lastHitActor.camp;
        detector.removeActor(ReflectUtil.cast(lastHitActor, VCharacter.class));
    }
}

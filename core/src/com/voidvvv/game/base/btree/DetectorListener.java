package com.voidvvv.game.base.btree;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.voidvvv.game.base.Camp;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VActorAdaptor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.utils.ReflectUtil;

import java.util.Set;

public class DetectorListener extends VActorAdaptor {
    public VCharacter character;
    public Fixture fixture;
    public Set<VCharacter> characters;
    @Override
    public void reset() {
        super.reset();
        fixture = null;
        character = null;
        characters = null;
    }

    @Override
    public void afterHitOnActor() {
        VActor lastHitActor = character.lastHitActor;
        Fixture lastThisFixture = character.lastThisFixture;
        Fixture lastOtherFixture = character.lastOtherFixture;
        if (lastThisFixture != fixture) {
            return;
        }
        UserData ud = ReflectUtil.cast(lastOtherFixture.getUserData(), UserData.class);
        if (ud == null || ud.isDerivative() || ud.getType() == UserData.B2DType.SENSOR) {
            return;
        }
        Camp camp = lastHitActor.camp;
        if (!character.camp.compatible(camp)) {
            return;
        }
        if (characters != null && ReflectUtil.cast(lastHitActor, VCharacter.class) != null) {
            characters.add(ReflectUtil.cast(lastHitActor, VCharacter.class));
        }
    }
}

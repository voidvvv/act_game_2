package com.box2d.testt;

import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.utils.ReflectUtil;

public class CollisionListener implements ContactListener {


    public CollisionListener() {
    }

    @Override
    public void beginContact(Contact contact) {
//        contact.getFixtureA().getBody().
        UserData dataA = ReflectUtil.cast(contact.getFixtureA().getUserData(), UserData.class);
        UserData dataB = ReflectUtil.cast(contact.getFixtureB().getUserData(), UserData.class);

        if (dataA !=null && dataB != null) {
            dataA.getActor().onHit(dataB.getActor());
            dataB.getActor().onHit(dataA.getActor());
        }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        UserData dataA = ReflectUtil.cast(contact.getFixtureA().getUserData(), UserData.class);
        UserData dataB = ReflectUtil.cast(contact.getFixtureB().getUserData(), UserData.class);

        if (dataA != null && dataB != null) {
            VCharacter castA = ReflectUtil.cast(dataA.getActor(), VCharacter.class);
            VCharacter castB = ReflectUtil.cast(dataB.getActor(), VCharacter.class);
            if (castA != null && castB != null) {
                contact.setEnabled(false);
                return;
            }
            if (!dataA.getActor().shoulCollide(dataB.getActor())  || !dataB.getActor().shoulCollide(dataA.getActor())) {
                contact.setEnabled(false);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

package com.box2d.testt;

import com.badlogic.gdx.physics.box2d.*;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.manager.SystemNotifyMessageManager;

public class CollisionListener implements ContactListener {

    SystemNotifyMessageManager systemNotifyMessageManager;

    public CollisionListener() {
        systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
    }

    @Override
    public void beginContact(Contact contact) {
//        contact.getFixtureA().getBody().
        System.out.println("beginContact");
        systemNotifyMessageManager.pushMessage("beginContact");
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("endContact");
        systemNotifyMessageManager.pushMessage("endContact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

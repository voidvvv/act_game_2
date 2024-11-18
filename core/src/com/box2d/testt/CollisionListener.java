package com.box2d.testt;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
//        contact.getFixtureA().getBody().
        System.out.println("beginContact");
    }

    @Override
    public void endContact(Contact contact) {
        System.out.println("endContact");

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        System.out.println("preSolve");
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        System.out.println("postSolve: " + impulse.getNormalImpulses()[0] + " " + impulse.getNormalImpulses()[1]);

    }
}

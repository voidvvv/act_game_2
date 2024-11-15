package com.voidvvv.game.context;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.b2d.UserData;

public class WorldContext {
    public static float GRAVITY_CONST = -9.8F;

    public static final short ROLE = 1;

    public static final short OBSTACLE = 1<<1;

    private static VWorld world;

    public static VWorld getWorld () {
        if (world == null) {
            world = new VWorld();
        }
 
        return world;
    }

    public static Fixture createRoleFixture(BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy) {
        return createFixture(bodyType,initX,initY,hx,hy,ROLE,OBSTACLE);
    }

    public static Fixture createObstacle(BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy) {
        return createFixture(bodyType,initX,initY,hx,hy,OBSTACLE,ROLE);
    }

    public static Fixture createFixture(BodyDef.BodyType bodyType, float initX, float initY, float hx, float hy, short category, short mask) {
        World box2dWorld = getWorld().getBox2dWorld();
        BodyDef bd = new BodyDef();
        bd.type = bodyType;
        bd.position.set(initX,initY);
        Body body = box2dWorld.createBody(bd);
//        body.setAngularDamping(1f);
        body.setFixedRotation(true);

        FixtureDef fd = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(hx,hy);
        fd.friction = 0;
        fd.density = 0.5f;
        fd.filter.categoryBits = category;
        fd.filter.maskBits = mask;
        fd.shape = polygonShape;
        Fixture fixture = body.createFixture(fd);
        UserData userData = new UserData();
        userData.setSubShifting(-hy);
        fixture.setUserData(userData);
        polygonShape.dispose();

        return fixture;
    }
}
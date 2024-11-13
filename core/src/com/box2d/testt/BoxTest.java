package com.box2d.testt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class BoxTest extends ApplicationAdapter {

    World world;
    Body body;

    Box2DDebugRenderer debugRenderer;

    OrthographicCamera orthographicCamera;

    Body body2;
    @Override
    public void create() {
        orthographicCamera = new OrthographicCamera();
        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new CollisionListener());
        createBody2(BodyDef.BodyType.StaticBody);
// First we create a body definition
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(5, 10);


// Create our body in the world using our body definition
        body = world.createBody(bodyDef);

        body.setLinearVelocity(1f,5f);
//        body.applyForceToCenter(10,10,true);
//        body.applyLinearImpulse(20,10,0,0,true);
// Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(5f);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f; // Make it bounce a little bit
        fixtureDef.filter.categoryBits = 4;
        fixtureDef.filter.maskBits = 2;
// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
//        body.setType();
// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();


//        body.setTransform(v2.set(0,35f),0f);

    }

    private void createBody2(BodyDef.BodyType bodyType) {
        BodyDef bd= new BodyDef();
        bd.type = bodyType;
        bd.position.set(0,30);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(10f,5f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f; // Make it bounce a little bit
        fixtureDef.filter.categoryBits = 2;
        fixtureDef.filter.maskBits = 4;;
//        fixtureDef.isSensor = true;
        body2  = world.createBody(bd);
        Fixture fixture = body2.createFixture(fixtureDef);
        bd.position.set(0,45);

        Body body1 = world.createBody(bd);
        body1.createFixture(fixtureDef);

//        fixture = body2;
        polygonShape.dispose();

    }

    @Override
    public void resize(int width, int height) {
        orthographicCamera.setToOrtho(false,width/10f,height/10f);
        orthographicCamera.update();
    }

    Vector2 v2 = new Vector2();
    @Override
    public void render() {
        ScreenUtils.clear(1, 0.5f, 0.5f, 1);
        world.step(1/60f, 6, 2);
        debugRenderer.render(world,orthographicCamera.combined);

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            body.setLinearVelocity(1,5f);
            body.setLinearVelocity(1,5f);

            body.setLinearVelocity(1,5f);
            body.setLinearVelocity(1,5f);
            body.setLinearVelocity(1,5f);

        }
    }
}

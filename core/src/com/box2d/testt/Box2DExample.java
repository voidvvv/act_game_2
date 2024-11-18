package com.box2d.testt;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Box2DExample extends ApplicationAdapter {
    private World world;
    private Body person;
    private Body ground;
    private float moveSpeed = 5.0f;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        // Set up the world with gravity (downward force)
        world = new World(new Vector2(0, -9.8f), true);  // Gravity along Y-axis

        // Create the ground (StaticBody)
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, -5);  // Position the ground just below the kinematic body
        ground = world.createBody(groundDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(50, 5);  // Large ground box
        ground.createFixture(groundShape, 0);  // 0 mass (static)
        groundShape.dispose();

        // Create the person (KinematicBody)
        BodyDef personDef = new BodyDef();
        personDef.type = BodyDef.BodyType.KinematicBody;
        personDef.position.set(0, 0);  // Position the person above the ground
        person = world.createBody(personDef);

        PolygonShape personShape = new PolygonShape();
        personShape.setAsBox(1, 2);  // Box shape for the person
        person.createFixture(personShape, 1.0f);  // Density for visualizing
        personShape.dispose();

        // Add the ContactListener to handle collisions
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Check if the kinematic person collides with the ground
                Body fixtureA = contact.getFixtureA().getBody();
                Body fixtureB = contact.getFixtureB().getBody();

                if (fixtureA == person || fixtureB == person) {
                    if (fixtureA == ground || fixtureB == ground) {
                        System.out.println("Person collided with ground!");
                        person.setLinearVelocity(Vector2.Zero);  // Stop movement on collision
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {}
        });

        // Set up debug renderer
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
    }

    @Override
    public void render() {
        // Step the simulation (60Hz)
        world.step(1 / 60f, 6, 2);  // Adjust iterations for better accuracy

        // Handle user input to move the person
        Vector2 velocity = person.getLinearVelocity().cpy();

        // Use keyboard inputs to move the person
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -moveSpeed;  // Move left
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = moveSpeed;  // Move right
        } else {
            velocity.x = 0;  // No movement
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            velocity.y = moveSpeed;  // Move up
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            velocity.y = -moveSpeed;  // Move down
        } else {
            velocity.y = 0;  // No movement
        }

        // Apply velocity to the kinematic body
        person.setLinearVelocity(velocity);

        // Clear the screen and redraw
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the debug view (to visualize the physics world)
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        // Clean up resources
        world.dispose();
    }
}

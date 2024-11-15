package com.voidvvv.game.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;

public class DrawManager implements Disposable {
    private SpriteBatch spriteBatch;

    Box2DDebugRenderer box2DDebugRenderer;
    private ShapeRenderer shapeRenderer;

    public DrawManager() {
    }

    public void init() {
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public Box2DDebugRenderer getBox2DDebugRenderer() {
        return box2DDebugRenderer;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    @Override
    public void dispose() {
        if (spriteBatch != null)
            spriteBatch.dispose();
    }
}

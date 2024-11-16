package com.voidvvv.game.context.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.voidvvv.game.ActGame;

public class Pinpoint extends Actor {
    public Vector2 position = new Vector2();
    public boolean running = false;
    public float time;
    public float maxTime = 2f;
    public float size = 5f;

    public final Color color = Color.RED.cpy();

    public void begin (float x, float y) {
        running = true;
        time = maxTime;
        position.set(x, y);
    }

    @Override
    public void act(float delta) {
        if (running) {
            time -= delta;
            if (time <= 0f) {
                running = false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!running) {
            return;
        }
        float alpha = parentAlpha * (time / maxTime);
        color.a = alpha;
        ActGame.gameInstance().getDrawManager().enableBlend();
        ShapeRenderer shapeRenderer = ActGame.gameInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.x(position, size);
        shapeRenderer.flush();
        ActGame.gameInstance().getDrawManager().disableBlend();

    }
}

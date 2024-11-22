package com.voidvvv.game.context.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.voidvvv.game.ActGame;

public class Pinpoint extends Actor {


    public final Color color = Color.RED.cpy();
    PinpointData pinpointData;


    public PinpointData getPinpointData() {
        return pinpointData;
    }

    public void setPinpointData(PinpointData pinpointData) {
        this.pinpointData = pinpointData;
    }

    public Pinpoint() {
        pinpointData = ActGame.gameInstance().currentWorld().getPinpointData();
    }

    @Override
    public void act(float delta) {
        if (pinpointData.running) {
            pinpointData.time -= delta;
            if (pinpointData.time <= 0f) {
                pinpointData.running = false;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (!pinpointData.running) {
            return;
        }
        batch.end();
        float alpha = parentAlpha * (pinpointData.time / pinpointData.maxTime);
        color.a = alpha;
        ActGame.gameInstance().getDrawManager().enableBlend();
        ShapeRenderer shapeRenderer = ActGame.gameInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.x(pinpointData.position, pinpointData.size);
        shapeRenderer.flush();
        shapeRenderer.end();
        ActGame.gameInstance().getDrawManager().disableBlend();
        batch.begin();

    }
}

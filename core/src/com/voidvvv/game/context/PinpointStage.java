package com.voidvvv.game.context;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.context.input.Pinpoint;

public class PinpointStage extends Stage {
    Pinpoint pinpoint;

    public void addPinpoint(Pinpoint pinpoint) {
        this.pinpoint = pinpoint;
        addActor(pinpoint);
    }

    public Pinpoint getPinpoint() {
        return pinpoint;
    }

    public void setPinpoint(Pinpoint pinpoint) {
        this.pinpoint = pinpoint;
    }

    public PinpointStage() {
        super();
        addPinpoint(new Pinpoint());
    }

    public PinpointStage(Viewport viewport) {
        super(viewport);
        addPinpoint(new Pinpoint());

    }

    public PinpointStage(Viewport viewport, Batch batch) {
        super(viewport, batch);
        addPinpoint(new Pinpoint());

    }

    @Override
    public void draw() {
        ActGame.gameInstance().getDrawManager().begin(getViewport().getCamera());
        getRoot().draw(ActGame.gameInstance().getDrawManager().getSpriteBatch(), 1);
        ActGame.gameInstance().getDrawManager().end();
    }
//
//    public void draw () {
//        Camera camera = viewport.getCamera();
//        camera.update();
//
//        if (!root.isVisible()) return;
//
//        Batch batch = this.batch;
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//
//        batch.end();
//
//        if (debug) drawDebug();
//    }


}

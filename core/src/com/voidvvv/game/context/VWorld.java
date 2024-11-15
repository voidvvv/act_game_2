package com.voidvvv.game.context;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;

import java.util.ArrayList;
import java.util.List;

public class VWorld {
    public static final VActor.VActorCompare DEFAULT_ACTOR_COMPARE = new VActor.VActorCompare();

    private Stage stage;

    private final List<VActor> actorList = new ArrayList<>();

    private final List<VActor> updateList = new ArrayList<>();

    private final List<VActor> renderList = new ArrayList<>();

    private World box2dWorld;

    public VWorld() {

    }

    public List<VActor> allActors () {

        return actorList;
    }

    public World getBox2dWorld() {
        return box2dWorld;
    }

    public <T extends VActor> T registerActor(T t) {
        stage.addActor(t);
        actorList.add(t);
        updateList.add(t);
        renderList.add(t);
        return t;
    }

    public void init () {
        initWorld();
        initStage();
    }

    private void initStage() {
        stage = new Stage(new ScreenViewport(ActGame.gameInstance().getCameraManager().getMainCamera())
                , ActGame.gameInstance().getDrawManager().getSpriteBatch());
        ActGame.gameInstance().addInputProcessor(stage);
    }

    private void initWorld() {
        box2dWorld = new World(new Vector2(0,0),false);
    }

    public void update (float delta) {
// physic

        box2dWorld.step(delta,10,10);
        stage.act(delta);
        actorList.sort(DEFAULT_ACTOR_COMPARE);

    }

}

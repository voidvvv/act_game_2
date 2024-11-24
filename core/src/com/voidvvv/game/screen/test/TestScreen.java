package com.voidvvv.game.screen.test;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;
import com.voidvvv.game.base.test.VObstacle;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.context.WorldHelper;
import com.voidvvv.game.context.input.CharacterInputListener;
import com.voidvvv.game.base.debug.VDebugShapeRender;
import com.voidvvv.game.base.test.Bob;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.screen.test.ui.TextMessageBar;

public class TestScreen extends ScreenAdapter {

    VDebugShapeRender debugShapeRender;

    SystemNotifyMessageManager systemNotifyMessageManager;

    OrthographicCamera orthographicCamera;
    OrthographicCamera screenCamera;

    BitmapFont font;

    SpriteBatch spriteBatch;


    Vector3 cameraPosLerp = new Vector3();

    Stage uiStage;

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.5f, 0.5f, 1);
        // UPDATE
        systemNotifyMessageManager.update(delta);
        uiStage.act(delta);

        VWorld vWorld = ActGame.gameInstance().currentWorld();
        vWorld.update(delta);

        orthographicCamera.position.lerp(cameraPosLerp.set(vWorld.getProtagonist().position.x,vWorld.getProtagonist().position.y,0.f),0.1f);

        vWorld.draw();

        vWorld.getStage().draw();
        uiStage.getViewport().apply();
        uiStage.draw();
    }
    Vector3 v3 = new Vector3();
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        VWorld vWorld = ActGame.gameInstance().currentWorld();

        vWorld.getStage().getViewport().update(width, height, false);
        uiStage.getViewport().update(width, height, false);

//        v3.set(orthographicCamera.position);
////        orthographicCamera.setToOrtho(false,width/3f,height/3f);
//        orthographicCamera.setToOrtho(false,320,240);
//
//
////        orthographicCamera.zoom = 0.2f;
//        orthographicCamera.position.set(v3);
//        v3.set(screenCamera.position);
//        screenCamera.setToOrtho(false,width,height);
//        screenCamera.position.set(v3);
//        screenCamera.update();

    }

    @Override
    public void show() {
        initParam();
        // load asset
        loadAsset();
        initWorld();
        initUI();
        initUserInput();
    }

    private void initUserInput() {
        ActGame.gameInstance().addInputProcessor(vWorld.getStage());
    }

    VWorld vWorld;
    private void initWorld() {
        WorldHelper worldHelper = WorldHelper.builder()
                .build();
        vWorld.init(worldHelper);
        VActorSpawnHelper helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.DynamicBody)
                .category((short)(WorldContext.ROLE|WorldContext.WHITE)) // who am I
                .mask((short)(WorldContext.OBSTACLE|WorldContext.BLACK|WorldContext.ROLE)) // who do I want to collision
                .hx(vWorld.unit()/2 - 2f).hy(2)
                .initX(60).initY(100)
                .build();
        Bob bob = vWorld.spawnVActor(Bob.class,helper);
        bob.setName("Bob");
        bob.getActualBattleAttr().attack = 500;
        vWorld.setProtagonist(bob);
        CharacterInputListener inputListener = new CharacterInputListener();
        inputListener.setCharacter(bob);
        vWorld.addListener(inputListener);
        // input
        CharacterInputListener characterInputListener = new CharacterInputListener();
        characterInputListener.setCharacter(bob);
        vWorld.addListener(characterInputListener);
        // obstacle
        helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.DynamicBody)
                .category((short)(WorldContext.ROLE|WorldContext.BLACK)) // who am I
                .mask((short)(WorldContext.OBSTACLE|WorldContext.WHITE)) // who do I want to collision
                .hx(4).hy(4)
                .sensor(true)
                .initX(200).initY(100)
                .build();
        Bob bob1 = vWorld.spawnVActor(Bob.class, helper);
        bob1.setName("Bob1");

        helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.StaticBody)
                .category((short)(WorldContext.OBSTACLE)) // who am I
                .mask((short)(WorldContext.ROLE)) // who do I want to collision
                .hx(40).hy(40)
                .occupy(true)
//                .setSensor(true)
                .initX(50).initY(50)
                .build();
        VObstacle vObstacle = vWorld.spawnVActor(VObstacle.class, helper);
        vObstacle.setName("Rocky!");

    }

    private void initParam() {
        screenCamera = ActGame.gameInstance().getCameraManager().getScreenCamera();
        font = ActGame.gameInstance().getFontManager().getBaseFont();
        spriteBatch = ActGame.gameInstance().getDrawManager().getSpriteBatch();
        systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
        debugShapeRender = new VDebugShapeRender();
        orthographicCamera = ActGame.gameInstance().getCameraManager().getMainCamera();
        vWorld = ActGame.gameInstance().currentWorld();
    }

    private void loadAsset() {
        AssetManager assetManager = ActGame.gameInstance().getAssetManager();
        // map
        assetManager.load("map/test/act_game_02.tmx", TiledMap.class);
        assetManager.load(AssetConstant.BOB_IMAGE,Texture.class);

        assetManager.finishLoading();
    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport(screenCamera), ActGame.gameInstance().getDrawManager().getSpriteBatch());
        uiStage.addActor(new TextMessageBar() );
        ActGame.gameInstance().addInputProcessor(uiStage);
    }
}

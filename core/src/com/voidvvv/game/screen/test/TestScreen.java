package com.voidvvv.game.screen.test;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;
import com.voidvvv.game.base.actors.Slime;
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
import com.voidvvv.game.utils.ReflectUtil;

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
        uiStage.getViewport().apply();
        uiStage.draw();
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        VWorld vWorld = ActGame.gameInstance().currentWorld();

        vWorld.getStage().getViewport().update(width, height, false);
        uiStage.getViewport().update(width, height, false);

        orthographicCamera.zoom = 0.7f;
        orthographicCamera.update();
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
        TiledMap map = ActGame.gameInstance().getAssetManager().get(AssetConstant.MAP_TEST_01, TiledMap.class);
        MapLayer objLayer = map.getLayers().get("main_obj");
        float birthPlaceX = 60f;
        float birthPlaceY = 100f;
        if (objLayer != null) {
            RectangleMapObject mapObject = ReflectUtil.cast(objLayer.getObjects().get("birthPlace"), RectangleMapObject.class);
            if (mapObject != null) {
                birthPlaceX = mapObject.getRectangle().x + objLayer.getOffsetX();
                birthPlaceY = mapObject.getRectangle().y - objLayer.getOffsetY();

            }
        }
        VActorSpawnHelper helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.DynamicBody)
                .category((short)(WorldContext.ROLE|WorldContext.WHITE)) // who am I
                .mask((short)(WorldContext.OBSTACLE|WorldContext.BLACK|WorldContext.ROLE)) // who do I want to collision
                .hx(vWorld.unit()/2 - 2f).hy(2)
                .initX(birthPlaceX).initY(birthPlaceY)
                .build();
        Bob bob = vWorld.spawnVActor(Bob.class,helper);
        bob.setName("Bob");
        bob.getActualBattleAttr().attack = 1;
        bob.getActualBattleAttr().mp = 1000;
        vWorld.setProtagonist(bob);

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
        bob1.setName("Bob enemy");
        bob1.getBattleComponent().actualBattleAttr.hp = 1300;
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

        addSlime();

    }

    private void addSlime() {
        VActorSpawnHelper helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.DynamicBody)
                .category((short)(WorldContext.ROLE|WorldContext.WHITE)) // who am I
                .mask((short)(WorldContext.OBSTACLE|WorldContext.BLACK|WorldContext.ROLE)) // who do I want to collision
                .hx(vWorld.unit()/2 - 2f).hy(2)
                .initX(100f).initY(100f)
                .build();
        Slime bob = vWorld.spawnVActor(Slime.class,helper);
        bob.setName("Slime");
        bob.getActualBattleAttr().moveSpeed = 30f;
        bob.getActualBattleAttr().attack = 1;
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
        assetManager.load(AssetConstant.MAP_TEST_01, TiledMap.class);
        assetManager.load(AssetConstant.BOB_IMAGE,Texture.class);

        assetManager.finishLoading();
    }

    private void initUI() {
        uiStage = new Stage(new ScreenViewport(screenCamera), ActGame.gameInstance().getDrawManager().getSpriteBatch());
        uiStage.addActor(new TextMessageBar() );
        ActGame.gameInstance().addInputProcessor(uiStage);
    }
}

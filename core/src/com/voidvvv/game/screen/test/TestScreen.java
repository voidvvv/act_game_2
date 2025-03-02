package com.voidvvv.game.screen.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;
import com.voidvvv.game.base.Camp;
import com.voidvvv.game.base.VActorAdaptor;
import com.voidvvv.game.base.actors.slime.Slime;
import com.voidvvv.game.base.b2d.UserData;
import com.voidvvv.game.base.btree.BTManager;
import com.voidvvv.game.base.test.VObstacle;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.context.WorldHelper;
import com.voidvvv.game.context.input.CharacterInputListener;
import com.voidvvv.game.base.debug.VDebugShapeRender;
import com.voidvvv.game.base.test.Bob;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.context.machenism.SlimeGenerateMechanism;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.render.actor.slime.SlimeSimpleRender;
import com.voidvvv.game.screen.test.ui.TextMessageBar;
import com.voidvvv.game.utils.ReflectUtil;

public class TestScreen extends ScreenAdapter implements Telegraph {

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
        ActGame.gameInstance().getBtManager().update(delta);
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

    }

    @Override
    public void show() {
        MessageManager.getInstance().addListener(this, ADD_SLIME);
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

        Bob bob = spawnBob(birthPlaceX, birthPlaceY);
        bob.setName("Bob");
        bob.getActualBattleAttr().attack = 1;
        bob.getActualBattleAttr().mp = 1000;
        bob.camp.campBit = Camp.POSITIVE;
        bob.taregtCamp.campBit = Camp.NEGATIVE;
        vWorld.setProtagonist(bob);

        // input
        CharacterInputListener characterInputListener = new CharacterInputListener();
        characterInputListener.setCharacter(bob);
        vWorld.addListener(characterInputListener);

        Bob bob1 = spawnBob(200f,100f);
        bob1.setName("Bob enemy");
        bob1.getBattleComponent().actualBattleAttr.hp = 1300;
        bob1.camp.campBit = Camp.NEGATIVE;
        bob1.taregtCamp.campBit = Camp.POSITIVE;

        VObstacle vObstacle = spawnObstacle(50f,50f);
        vObstacle.setName("Rocky!");

//        addSlime();


        AddSlimeTest addSlimeTest = new AddSlimeTest();
        addSlimeTest.world = vWorld;
        vWorld.getStage().addActor(addSlimeTest);
        addSlimeTest.init();

        SlimeGenerateMechanism slimeGenerateMechanism = new SlimeGenerateMechanism();
        slimeGenerateMechanism.vWorld = vWorld;
        vWorld.addUpdateable(slimeGenerateMechanism);
    }

    private VObstacle spawnObstacle(float x, float y) {
        VActorSpawnHelper helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.StaticBody)
                .category((short)(WorldContext.OBSTACLE)) // who am I
                .mask((short)(WorldContext.ROLE)) // who do I want to collision
                .hx(40).hy(40)
                .hz(vWorld.unit())
                .occupy(true)
//                .setSensor(true)
                .initX(x).initY(y)
                .build();
        return vWorld.spawnVActor(VObstacle.class, helper);
    }

    public Bob spawnBob(float birthPlaceX, float birthPlaceY) {
        VActorSpawnHelper helper = VActorSpawnHelper.builder()
                .bodyType(BodyDef.BodyType.DynamicBody)
                .category((short)(WorldContext.ROLE|WorldContext.WHITE)) // who am I
                .mask((short)(WorldContext.OBSTACLE|WorldContext.BLACK|WorldContext.ROLE)) // who do I want to collision
                .hx(vWorld.unit()/2 - 2f).hy(2)
                .hz(vWorld.unit())
                .initX(birthPlaceX).initY(birthPlaceY)
                .build();
        return vWorld.spawnVActor(Bob.class,helper);

    }

    SlimeSimpleRender slimeSimpleRender;



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

    public final static int ADD_SLIME = 0xFFFF;
    Vector2 tmpV2 = new Vector2();
    @Override
    public boolean handleMessage(Telegram msg) {
        if (msg.message == ADD_SLIME) {
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            vWorld.getStage().screenToStageCoordinates(tmpV2.set(x,y));
            addSlime(tmpV2.x, tmpV2.y);
            return true;
        }
        return false;
    }

    public void addSlime(float x, float y) {
        if (slimeSimpleRender == null) {
            slimeSimpleRender = new SlimeSimpleRender();
            slimeSimpleRender.init();
        }

        Slime slime = vWorld.spawnVActor(Slime.class, x, y);
        settingForSlime(slime);
    }

    private void settingForSlime(Slime slime) {
        slime.setName("Slime");
        slime.getActualBattleAttr().moveSpeed = 30f;
        slime.getActualBattleAttr().attack = 1;
        slime.getActualBattleAttr().maxHp = 10;
        slime.getActualBattleAttr().hp = 10;
        slime.camp.campBit = Camp.NEGATIVE;
        slime.taregtCamp.campBit = Camp.POSITIVE;
        slime.render = slimeSimpleRender;
        ActGame.gameInstance().getBtManager().addTree(slime, BTManager.SLIME_SIMPLE);
    }
}

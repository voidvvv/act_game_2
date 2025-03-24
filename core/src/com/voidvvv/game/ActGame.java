package com.voidvvv.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.voidvvv.game.base.actors.ActorConstants;
import com.voidvvv.game.base.btree.BTManager;
import com.voidvvv.game.context.world.VWorld;
import com.voidvvv.game.context.world.WorldContext;
import com.voidvvv.game.manager.AudioManager;
import com.voidvvv.game.manager.CameraManager;
import com.voidvvv.game.manager.DrawManager;
import com.voidvvv.game.manager.FontManager;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.manager.event.VWorldEventManager;
import com.voidvvv.game.screen.test.TestScreen;

import java.security.SecureRandom;
import java.util.Random;

public class ActGame extends Game {
    public long seed;
    public float totalGameTime = 0f;
    private long frameId = 0;
    private static ActGame gameInstance;

    // context
    private WorldContext worldContext;


    // input
    InputMultiplexer inputMultiplexer;

    // manager
    private DrawManager drawManager;
    private FontManager fontManager;
    private CameraManager cameraManager;
    private VWorldEventManager vWorldEventManager;
    private SystemNotifyMessageManager systemNotifyMessageManager;
    private AssetManager assetManager;
    private AudioManager audioManager;
    private BTManager btManager;

    // screen
    private TestScreen testScreen;

    // delta time

    private ActGame() {
        drawManager = new DrawManager();
        fontManager = new FontManager();
        cameraManager = new CameraManager();
        vWorldEventManager = new VWorldEventManager();
        worldContext = new WorldContext();
        inputMultiplexer = new InputMultiplexer();
        systemNotifyMessageManager = new SystemNotifyMessageManager();
        assetManager = new AssetManager();
        audioManager = new AudioManager();
        btManager = new BTManager();
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    public static ActGame gameInstance() {
        if (gameInstance == null) {
            gameInstance = new ActGame();
        }
        return gameInstance;
    }

    @Override
    public void create() {
        // manager
        initRandom();
        initManagers();
        // init opt
        initOpt();

        testScreen = new TestScreen();
        setScreen(testScreen);
        // other

    }

    private void initRandom() {
        Random rd = new SecureRandom();
        seed = rd.nextLong();
        MathUtils.random.setSeed(seed);
    }


    private void initOpt() {

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void initManagers() {
        drawManager.init();
        fontManager.initFont();
        cameraManager.init();
        vWorldEventManager.init();
        worldContext.init();
        systemNotifyMessageManager.init();
        assetManager.setLoader(TiledMap.class,new TmxMapLoader());

        initBTManager();
    }

    private void initBTManager() {
        btManager.init();
        MessageManager.getInstance().addListener(btManager, ActorConstants.MSG_RESET_ACTOR);
    }

    public BTManager getBtManager() {
        return btManager;
    }

    public long getFrameId() {
        return frameId;
    }

    public SystemNotifyMessageManager getSystemNotifyMessageManager() {
        return systemNotifyMessageManager;
    }

    @Override
    public void render() {
        frameId++;
        totalGameTime += Gdx.graphics.getDeltaTime();
        super.render();
    }

    @Override
    public void dispose() {
        drawManager.dispose();
    }

    public FontManager getFontManager() {
        return fontManager;
    }

    public DrawManager getDrawManager() {
        return drawManager;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void addInputProcessor(InputProcessor inputProcessor) {
        inputMultiplexer.addProcessor(inputProcessor);
    }

    public void removeInputProcessor(InputProcessor inputProcessor) {
        inputMultiplexer.removeProcessor(inputProcessor);
    }

    public VWorld currentWorld() {
        return worldContext.currentWorld();
    }

    public VWorldEventManager getvWorldEventManager() {
        return vWorldEventManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }
}

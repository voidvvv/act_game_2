package com.voidvvv.game.screen.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.test.VObstacle;
import com.voidvvv.game.context.PinpointStage;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.context.input.CharacterInputListener;
import com.voidvvv.game.base.debug.VDebugShapeRender;
import com.voidvvv.game.base.test.Bob;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.context.map.VMapNode;
import com.voidvvv.game.manager.SystemNotifyMessageManager;
import com.voidvvv.game.screen.test.ui.TextMessageBar;

public class TestScreen extends ScreenAdapter {

    VDebugShapeRender debugShapeRender;

    SystemNotifyMessageManager systemNotifyMessageManager;

    OrthographicCamera orthographicCamera;
    OrthographicCamera screenCamera;

    BitmapFont font;

    SpriteBatch spriteBatch;

    Bob bob;

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

        orthographicCamera.position.lerp(cameraPosLerp.set(bob.position.x,bob.position.y,0.f),0.1f);
        orthographicCamera.update();


        debugShapeRender.begin(orthographicCamera.combined);
        debugShapeRender.render(vWorld);
        debugShapeRender.end();

        vWorld.getStage().draw();
        uiStage.draw();
    }
    Vector3 v3 = new Vector3();
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        v3.set(orthographicCamera.position);
//        orthographicCamera.setToOrtho(false,width/3f,height/3f);
        orthographicCamera.setToOrtho(false,640,480);

        orthographicCamera.position.set(v3);
        orthographicCamera.update();

        v3.set(screenCamera.position);
        screenCamera.setToOrtho(false,640,480);
        screenCamera.position.set(v3);
        screenCamera.update();
        VWorld vWorld = ActGame.gameInstance().currentWorld();
        vWorld.getStage().getViewport().update(width, height, true);
        uiStage.getViewport().update(width, height, true);

    }

    @Override
    public void show() {
        screenCamera = ActGame.gameInstance().getCameraManager().getScreenCamera();
        VWorld vWorld = ActGame.gameInstance().currentWorld();
        font = ActGame.gameInstance().getFontManager().getBaseFont();
        spriteBatch = ActGame.gameInstance().getDrawManager().getSpriteBatch();
        systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
        VActorSpawnHelper helper = VActorSpawnHelper.VActorSpawnHelperBuilder.builder()
                .setBodyType(BodyDef.BodyType.DynamicBody)
                .setCategory((short)(WorldContext.ROLE|WorldContext.WHITE)) // who am I
                .setMask((short)(WorldContext.OBSTACLE|WorldContext.BLACK|WorldContext.ROLE)) // who do I want to collision
                .setHx(4).setHy(4)
                .setInitX(60).setInitY(100)
                .build();
        bob = vWorld.spawnVActor(Bob.class,helper);
        // input
        CharacterInputListener characterInputListener = new CharacterInputListener();
        characterInputListener.setCharacter(bob);
        vWorld.addListener(characterInputListener);
        // obstacle
        helper = VActorSpawnHelper.VActorSpawnHelperBuilder.builder()
                .setBodyType(BodyDef.BodyType.DynamicBody)
                .setCategory((short)(WorldContext.ROLE)) // who am I
                .setMask((short)(WorldContext.OBSTACLE|WorldContext.WHITE)) // who do I want to collision
                .setHx(4).setHy(4)
                .setSensor(true)
                .setInitX(200).setInitY(100)
                .build();
        vWorld.spawnVActor(Bob.class,helper);

        helper = VActorSpawnHelper.VActorSpawnHelperBuilder.builder()
                .setBodyType(BodyDef.BodyType.StaticBody)
                .setCategory((short)(WorldContext.OBSTACLE)) // who am I
                .setMask((short)(WorldContext.ROLE)) // who do I want to collision
                .setHx(40).setHy(40)
                .setOccupy(true)
//                .setSensor(true)
                .setInitX(50).setInitY(50)
                .build();
        vWorld.spawnVActor(VObstacle.class,helper);

        debugShapeRender = new VDebugShapeRender();
        orthographicCamera = ActGame.gameInstance().getCameraManager().getMainCamera();

        initUI();

        ActGame.gameInstance().addInputProcessor(vWorld.getStage());

    }

    private void initUI() {
        uiStage = new Stage(new ScalingViewport(Scaling.stretch,640,480,screenCamera), ActGame.gameInstance().getDrawManager().getSpriteBatch());
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Image image = new Image(texture);
//        image.setWidth();
        image.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("click!!");
                return true;
            }
        });
//        uiStage.addActor(image);
        uiStage.addActor(new TextMessageBar() );
        ActGame.gameInstance().addInputProcessor(uiStage);
    }
}

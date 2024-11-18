package com.voidvvv.game.screen.test;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.ScreenUtils;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.test.VObstacle;
import com.voidvvv.game.context.VActorSpawnHelper;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.context.input.CharacterInputListener;
import com.voidvvv.game.base.debug.VDebugShapeRender;
import com.voidvvv.game.base.test.Bob;
import com.voidvvv.game.context.WorldContext;
import com.voidvvv.game.context.map.VMapNode;

public class TestScreen extends ScreenAdapter {

    VDebugShapeRender debugShapeRender;


    OrthographicCamera orthographicCamera;

    BitmapFont font;

    SpriteBatch spriteBatch;

    Bob bob;

    Vector3 cameraPosLerp = new Vector3();

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.5f, 0.5f, 1);
        // UPDATE
        VWorld vWorld = ActGame.gameInstance().currentWorld();
        vWorld.update(delta);

        orthographicCamera.position.lerp(cameraPosLerp.set(bob.position.x,bob.position.y,0.f),0.1f);
        orthographicCamera.update();

        debugShapeRender.begin(orthographicCamera.combined);
        debugShapeRender.render(vWorld);
        debugShapeRender.end();

        vWorld.getStage().draw();

    }
    Vector3 v3 = new Vector3();
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        v3.set(orthographicCamera.position);
        orthographicCamera.setToOrtho(false,width/3f,height/3f);
        orthographicCamera.position.set(v3);

        orthographicCamera.update();
        VWorld vWorld = ActGame.gameInstance().currentWorld();
        vWorld.getStage().getViewport().update(width, height, true);


    }

    @Override
    public void show() {
        VWorld vWorld = ActGame.gameInstance().currentWorld();
        font = ActGame.gameInstance().getFontManager().getBaseFont();
        spriteBatch = ActGame.gameInstance().getDrawManager().getSpriteBatch();

        VActorSpawnHelper helper = VActorSpawnHelper.VActorSpawnHelperBuilder.builder()
                .setBodyType(BodyDef.BodyType.DynamicBody)
                .setCategory((short)(WorldContext.ROLE|WorldContext.WHITE)) // who am I
                .setMask((short)(WorldContext.OBSTACLE|WorldContext.BLACK)) // who do I want to collision
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
//                .setSensor(true)
                .setInitX(50).setInitY(50)
                .build();
        vWorld.spawnVActor(VObstacle.class,helper);

        debugShapeRender = new VDebugShapeRender();
        orthographicCamera = ActGame.gameInstance().getCameraManager().getMainCamera();


    }
}

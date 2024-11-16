package com.voidvvv.game.screen.test;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.test.VObstacle;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.context.input.CharacterInputListener;
import com.voidvvv.game.base.debug.VDebugShapeRender;
import com.voidvvv.game.base.test.Bob;
import com.voidvvv.game.context.WorldContext;

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

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        orthographicCamera.setToOrtho(false,width/3f,height/3f);
        orthographicCamera.update();
    }

    @Override
    public void show() {
        VWorld vWorld = ActGame.gameInstance().currentWorld();
        font = ActGame.gameInstance().getFontManager().getBaseFont();
        spriteBatch = ActGame.gameInstance().getDrawManager().getSpriteBatch();

        bob = vWorld.spawnVActor(Bob.class,60, 100, 4, 4);
        vWorld.spawnVActorObstacle(VObstacle.class,1, 1, 20, 30);
        CharacterInputListener characterInputListener = new CharacterInputListener();
        characterInputListener.setCharacter(bob);
        vWorld.addListener(characterInputListener);

        debugShapeRender = new VDebugShapeRender();
        orthographicCamera = new OrthographicCamera();


    }
}

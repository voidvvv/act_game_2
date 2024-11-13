package com.voidvvv.game.screen.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.voidvvv.game.asset.font.FontManager;
import com.voidvvv.game.base.VPhysicAttr;
import com.voidvvv.game.base.debug.VDebugShapeRender;
import com.voidvvv.game.base.shape.VCube;
import com.voidvvv.game.base.test.Bob;
import com.voidvvv.game.base.test.VObstacle;
import com.voidvvv.game.context.VWorld;
import com.voidvvv.game.context.VWorldRunnable;
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
        WorldContext.getWorld().update(delta);

        orthographicCamera.position.lerp(cameraPosLerp.set(bob.position.x,bob.position.y,0.f),0.1f);
        orthographicCamera.update();

        debugShapeRender.begin(orthographicCamera.combined);
        debugShapeRender.render(WorldContext.getWorld());
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
        bob = new Bob();
        bob.init();
        VObstacle obs = new VObstacle();
        obs.initX = 60;
        obs.initY = 100;
        obs.hx = 20;
        obs.hy = 30;
        obs.init();
        debugShapeRender = new VDebugShapeRender();
        orthographicCamera = new OrthographicCamera();

        font = FontManager.getInstance().getBaseFont();
        spriteBatch = new SpriteBatch();
        WorldContext.getWorld().init();
    }
}

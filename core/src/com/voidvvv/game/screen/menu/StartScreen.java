package com.voidvvv.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.asset.AssetConstant;

public class StartScreen extends ScreenAdapter {
    public StartScreen(ActGame actGame) {
        this.actGame = actGame;
    }
    Stage baseStage;
    ScreenViewport screenViewport = null;
    ActGame actGame;
    Texture testPic;
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.5f, 0.5f, 1);

        baseStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        screenViewport.update(width,height, true);
        button.setPosition(width/2 - button.getWidth()/2, height/2 - button.getHeight()/2);
    }
    TextButton button;
    @Override
    public void show() {
        loadAssets();

        OrthographicCamera screenCamera = actGame.getCameraManager().getScreenCamera();
        screenViewport = new ScreenViewport(screenCamera);

        baseStage = new Stage(screenViewport);
        Texture texture = actGame.getAssetManager().get("badlogic.jpg", Texture.class);
        Sprite sprite = new Sprite(texture);
        button = new TextButton("Start", new Skin(Gdx.files.internal("image/actor/ui/skin/neon/neon-ui.json")));

//        button.setBounds(20,20,50,50);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                actGame.setScreen(actGame.getTestScreen());
            }
        });
        baseStage.addActor(button);
        actGame.addInputProcessor(baseStage);
    }

    private void loadAssets() {
        AssetManager assetManager = ActGame.gameInstance().getAssetManager();
        assetManager.load(AssetConstant.BOB_IMAGE, Texture.class);
        assetManager.load("badlogic.jpg", Texture.class);
        assetManager.finishLoading();
        testPic = actGame.getAssetManager().get(AssetConstant.BOB_IMAGE, Texture.class);

    }

    @Override
    public void dispose() {
        super.dispose();
        actGame.removeInputProcessor(baseStage);
        baseStage.dispose();
    }
}

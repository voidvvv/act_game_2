package com.voidvvv.game.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

public class FontManager implements Disposable {
    BitmapFont baseFont;


    public BitmapFont getBaseFont() {
        if (baseFont == null) {
            initFont();
        }
        return baseFont;
    }

    public void initFont()  {
//        if (Gdx.app.getType() == Application.ApplicationType.WebGL) {
//        } else {
//            Object fontGenerator = Class.forName("com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator").getConstructor(FileHandle.class).newInstance(Gdx.files.absolute("C:/Windows/Fonts/msyh.ttc"));
////
//            FreeTypeFontGenerator generator = null;
////            generator = new FreeTypeFontGenerator(Gdx.files.absolute("C:/Windows/Fonts/msyh.ttc"));
//            FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
//            Class<?> paramClass = Class.forName("com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter");
//            Object fontParam = paramClass.getConstructor().newInstance();
//            paramClass.getDeclaredField("incremental").set(fontParam, true);
//            paramClass.getDeclaredField("color").set(fontParam, Color.BLUE);
//            paramClass.getDeclaredField("size").set(fontParam, 20);
////            param.incremental = true;
////            param.color = Color.BLUE;
////            param.size = 20;
//            Method[] declaredMethods = paramClass.getDeclaredMethods();
//
//            baseFont = generator.generateFont(param);
//        }
        baseFont = new BitmapFont(Gdx.files.internal("font/yizi.fnt"), new TextureRegion(new Texture(Gdx.files.internal("font/yizi.png"))));

        baseFont.getData().markupEnabled = true;
    }

    @Override
    public void dispose() {
        this.baseFont.dispose();
    }
}

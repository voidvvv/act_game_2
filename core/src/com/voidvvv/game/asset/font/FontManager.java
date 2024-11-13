package com.voidvvv.game.asset.font;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FontManager {
    private static FontManager instance = new FontManager();
    BitmapFont baseFont;

    public static FontManager getInstance() {
        return instance;
    }

    private FontManager() {
    }

    public BitmapFont getBaseFont() {
        if (baseFont == null) {

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

    }
}

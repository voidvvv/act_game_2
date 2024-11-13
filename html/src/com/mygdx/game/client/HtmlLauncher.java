package com.mygdx.game.client;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.voidvvv.game.ActGame;

public class HtmlLauncher extends GwtApplication {
    @Override
    public void onModuleLoad() {
        super.onModuleLoad();
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                Gdx.app.log("aaa", "bbb asd");
//                                if (Gdx.graphics.isFullscreen()) {
//                                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
//                                } else {
//                                        int width = event.getWidth() - 10;
//                                        int height = event.getHeight() - 10;
//                                        getRootPanel().setWidth("" + width + "px");
//                                        getRootPanel().setHeight("" + height + "px");
//                                        getApplicationListener().resize(width, height);
//                                        Gdx.graphics.setWindowedMode(width, height);
//                                }
            }
        });
    }

    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration applicationConfiguration = new GwtApplicationConfiguration(true);
        setLogLevel(Application.LOG_INFO);
//                Gdx.graphics.setWindowedMode(width, height);
//                applicationConfiguration.log = new TextArea();
        // Resizable application, uses available space in browser
        return applicationConfiguration;
        // Fixed size application:
        //return new GwtApplicationConfiguration(480, 320);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return ActGame.gameInstance();
    }
}
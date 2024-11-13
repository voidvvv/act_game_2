package com.voidvvv.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.voidvvv.game.screen.test.TestScreen;

import java.util.Stack;

public class ActGame extends Game {

    private long frameId = 0;
    private static  ActGame gameInstance;

    private TestScreen testScreen;
    private ActGame(){};

    public static ActGame gameInstance() {
        if (gameInstance == null) {
            gameInstance = new ActGame();
        }
        return gameInstance;
    }

    @Override
    public void create() {
        testScreen = new TestScreen();
        setScreen(testScreen);
    }

    public long getFrameId() {
        return frameId;
    }

    @Override
    public void render() {
        frameId++;
        super.render();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");

    }
}

package com.voidvvv.game.screen.test.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.manager.FontManager;
import com.voidvvv.game.manager.SystemNotifyMessage;
import com.voidvvv.game.manager.SystemNotifyMessageManager;

import java.util.ArrayDeque;
import java.util.Deque;

public class TextMessageBar extends Actor {

    private SystemNotifyMessageManager systemNotifyMessageManager;

    private FontManager fontManager;


    public TextMessageBar() {
        systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
        fontManager = ActGame.gameInstance().getFontManager();
        setPosition(100,100);
    }



    @Override
    public void act(float delta) {
        super.act(delta);
    }
    Color color = Color.RED.cpy();
    @Override
    public void draw(Batch batch, float parentAlpha) {
        float shifting = getY();

        BitmapFont baseFont = fontManager.getBaseFont();

        for (SystemNotifyMessage msg : systemNotifyMessageManager.messages) {
            String message = msg.getMessage();
            if (message!=null ) {
                baseFont.getColor().a = parentAlpha * msg.time / systemNotifyMessageManager.maxTime;
                baseFont.draw(batch, message, getX(), shifting);
                shifting+=baseFont.getLineHeight() + 0.5f;
            }
        }
    }
}

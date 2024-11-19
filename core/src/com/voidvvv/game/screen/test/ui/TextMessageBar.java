package com.voidvvv.game.screen.test.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.manager.FontManager;
import com.voidvvv.game.manager.SystemNotifyMessage;
import com.voidvvv.game.manager.SystemNotifyMessageManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class TextMessageBar extends Actor {

    private SystemNotifyMessageManager systemNotifyMessageManager;

    private FontManager fontManager;

    private DragListener dragListener;

    Vector2 lastPosition = new Vector2();
    Vector2 currentPosition = new Vector2();
    Vector2 lastActorPosition = new Vector2();
    public TextMessageBar() {
        dragListener = new DragListener() {

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                TextMessageBar.this.lastPosition.set(event.getStageX(),event.getStageY());
                lastActorPosition.set(getX(),getY());
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                currentPosition.set(event.getStageX(),event.getStageY());
            }
        };
        systemNotifyMessageManager = ActGame.gameInstance().getSystemNotifyMessageManager();
        fontManager = ActGame.gameInstance().getFontManager();
//        setPosition(100,100);
        setBounds(100,100,500,100);
        BitmapFont baseFont = fontManager.getBaseFont();
        baseFont.setColor(color);
        addListener(dragListener);
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if (dragListener.isDragging()) {
            this.setPosition(lastActorPosition.x + currentPosition.x -  lastPosition.x, lastActorPosition.y + currentPosition.y -  lastPosition.y);
        }
    }
    Color color = Color.BLUE.cpy();
    @Override
    public void draw(Batch batch, float parentAlpha) {
        BitmapFont baseFont = fontManager.getBaseFont();

        float shifting = getY() + baseFont.getLineHeight() + 0.5f;

        ActGame.gameInstance().getDrawManager().enableBlend();
        baseFont.getColor().a = 1f;

        baseFont.draw(batch, getX() + " - " + getY(), getX(), shifting - baseFont.getLineHeight() - 0.5f);
        Iterator<SystemNotifyMessage> it = systemNotifyMessageManager.messages.descendingIterator();
        while (it.hasNext()) {
            SystemNotifyMessage msg = it.next();
            String message = msg.getMessage();

            if (message!=null ) {
                baseFont.getColor().a = parentAlpha * msg.time / systemNotifyMessageManager.maxTime;
                baseFont.draw(batch, message, getX() + 2f, shifting);
                shifting+=baseFont.getLineHeight() + 0.5f;
            }
        }
        ActGame.gameInstance().getDrawManager().disableBlend();
        ShapeRenderer shapeRenderer = ActGame.gameInstance().getDrawManager().getShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(ActGame.gameInstance().getCameraManager().getScreenCamera().combined);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
    }
}

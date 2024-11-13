package com.voidvvv.game.controller;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

public class BaseInputController implements Telegraph {

    public void move() {
        MessageManager.getInstance().dispatchMessage(1,"a");
    }

    @Override
    public boolean handleMessage(Telegram telegram) {
        return false;
    }
}

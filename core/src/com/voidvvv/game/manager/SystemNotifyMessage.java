package com.voidvvv.game.manager;

public class SystemNotifyMessage {
    private String message;
    public float time;

    public void update (float delta) {
        time-=delta;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

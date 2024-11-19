package com.voidvvv.game.manager;

public class SystemNotifyMessage {
    private String message;
    public float time;
    public int num;

    public void update (float delta) {
        time-=delta;
    }

    public String getMessage() {
        return num + ": " + message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

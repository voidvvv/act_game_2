package com.voidvvv.game.v2.base;

import com.badlogic.gdx.utils.Pool;

public interface VActor extends Pool.Poolable {


    public abstract void init();

    public abstract void update(float delta);

    public abstract void reset();


    void draw();

    public <T> T getAtt(int type);

    public <T>  void setAtt(int type, T value);

    void setState(VActorMetaState state);

    VActorMetaState getState();

}

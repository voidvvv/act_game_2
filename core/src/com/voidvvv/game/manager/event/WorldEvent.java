package com.voidvvv.game.manager.event;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.VActor;

public abstract class WorldEvent implements Pool.Poolable {
    public long frameIndex;
    protected VActor targetActor;
    protected VActor fromActor;
    protected Object extraInfo;

    public Object getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Object extraInfo) {
        this.extraInfo = extraInfo;
    }

    public VActor getFromActor() {
        return fromActor;
    }

    public void setFromActor(VActor fromActor) {
        this.fromActor = fromActor;
    }

    public VActor getTargetActor() {
        return targetActor;
    }

    public void setTargetActor(VActor targetActor) {
        this.targetActor = targetActor;
    }

    public abstract void apply();
    public abstract void postApply();
    public void apply(float delta) {
        apply();
    };

    @Override
    public void reset() {
        this.fromActor = null;
        this.targetActor = null;
    }
}

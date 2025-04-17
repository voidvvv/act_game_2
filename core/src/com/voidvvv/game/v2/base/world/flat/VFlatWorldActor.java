package com.voidvvv.game.v2.base.world.flat;

import com.voidvvv.game.v2.base.VActorMetaState;
import com.voidvvv.game.v2.base.VActorRender;
import com.voidvvv.game.v2.base.world.VWorldActor;

public class VFlatWorldActor extends VWorldActor {
    public static class Attribute {
        public static final int BOX_2D_WORLD = 1001;
    }
    private VActorRender actorRender;
    @Override
    public void init() {
        actorRender.init();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void draw() {

    }

    @Override
    public <T> T getAtt(int type) {
        Object result = super.getAtt(type);
        if (result != null) {
            return (T)result;
        }
        if (type == Attribute.BOX_2D_WORLD) {
            return (T) getWorldContext().getWorld();
        }
        return null;
    }

    @Override
    public <T> void setAtt(int type, T value) {
        super.setAtt(type , value);
    }

    @Override
    public void setState(VActorMetaState state) {

    }

    @Override
    public VActorMetaState getState() {
        return null;
    }
}

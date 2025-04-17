package com.voidvvv.game.v2.base.world;

import com.voidvvv.game.context.world.WorldContext;
import com.voidvvv.game.v2.base.VAttrActor;

public abstract class VWorldActor extends VAttrActor {
    protected WorldContext worldContext;
    public WorldContext getWorldContext() {
        return worldContext;
    };

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }
}

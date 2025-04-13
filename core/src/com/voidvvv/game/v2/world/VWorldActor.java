package com.voidvvv.game.v2.world;

import com.voidvvv.game.context.world.WorldContext;
import com.voidvvv.game.v2.VActor;
import com.voidvvv.game.v2.VAttrActor;

public abstract class VWorldActor extends VAttrActor {
    protected WorldContext worldContext;
    public WorldContext getWorldContext() {
        return worldContext;
    };

    public void setWorldContext(WorldContext worldContext) {
        this.worldContext = worldContext;
    }
}

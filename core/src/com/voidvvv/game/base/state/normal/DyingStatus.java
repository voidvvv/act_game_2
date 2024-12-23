package com.voidvvv.game.base.state.normal;

import com.badlogic.gdx.Gdx;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.state.VCharactorStatus;

public class DyingStatus extends VCharactorStatus {
    public static final DyingStatus INSTANCE = new DyingStatus();

    public DyingStatus() {}
    @Override
    public void exec(VCharacter entity) {
        if (entity.statusTime >= 1f) {
            entity.getWorld().destroyActor(entity);
        } else {
            entity.statusTime += Gdx.graphics.getDeltaTime();
            entity.statusProgress += Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void enter(VCharacter entity) {
        Gdx.app.log("DyingStatus", entity.getName() + " dying!");
        entity.statusTime = 0f;
        entity.statusProgress = 0f;
    }

    @Override
    public void exit(VCharacter entity) {
        entity.statusTime = 0f;
        entity.statusProgress = 0f;
    }
}

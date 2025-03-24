package com.voidvvv.game.context.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.box2d.testt.CollisionListener;
import com.voidvvv.game.context.BaseBattleContext;
import com.voidvvv.game.manager.event.VWorldEventManager;
import lombok.Builder;

@Builder
public class WorldHelper {
    public TiledMap map;
    public VWorldEventManager worldEventManager;
    public CollisionListener collisionListener;
    public Stage stage;
    public BaseBattleContext battleContext;
    public InputListener inputListener;
}

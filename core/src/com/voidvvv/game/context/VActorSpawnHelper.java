package com.voidvvv.game.context;

import com.badlogic.gdx.physics.box2d.BodyDef;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class VActorSpawnHelper {
    BodyDef.BodyType bodyType;
    float initX;
    float initY;
    float hx;
    float hy;
    float hz;
    short category;
    short mask;
    float friction = 0f;
    float density = 0.5f;

    boolean sensor = false;

    boolean occupy = false;
    Object userData;

}

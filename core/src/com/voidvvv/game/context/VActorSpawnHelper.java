package com.voidvvv.game.context;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.voidvvv.game.base.b2d.UserData;
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
    float hz = 16f;
    long category;
    long mask;
    float friction = 0f;
    float density = 0.5f;

    boolean sensor = false;

    boolean occupy = false;
    Object userData;
    UserData.B2DType bdType;
    boolean derivative;

}

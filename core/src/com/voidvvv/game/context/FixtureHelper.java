package com.voidvvv.game.context;

import com.voidvvv.game.base.b2d.UserData;

public class FixtureHelper {
    public float x1;
    public float y1;
    public float x2;
    public float y2;

    public boolean derivative;
    public short box2dMask;
    public short box2dCategory;
    public boolean sensor;

    public UserData.B2DType type = UserData.B2DType.GROUND;
}

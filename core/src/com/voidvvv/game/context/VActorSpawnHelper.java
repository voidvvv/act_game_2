package com.voidvvv.game.context;

import com.badlogic.gdx.physics.box2d.BodyDef;

public class VActorSpawnHelper {
    BodyDef.BodyType bodyType;
    float initX;
    float initY;
    float hx;
    float hy;
    short category;
    short mask;
    float friction = 0f;
    float density = 0.5f;

    boolean sensor = false;
    Object userData;

    public boolean isSensor() {
        return sensor;
    }

    public void setSensor(boolean sensor) {
        this.sensor = sensor;
    }

    // builder
    public static class VActorSpawnHelperBuilder {
        BodyDef.BodyType bodyType;
        float initX;
        float initY;
        float hx;
        float hy;
        short category;
        short mask;
        float friction;
        float density;
        boolean sensor = false;
        Object userData;

        public static VActorSpawnHelperBuilder builder() {
            return new VActorSpawnHelperBuilder();
        }

        private VActorSpawnHelperBuilder() {}

        public VActorSpawnHelperBuilder setBodyType(BodyDef.BodyType bodyType) {
            this.bodyType = bodyType;
            return this;
        }
        public VActorSpawnHelperBuilder setInitX(float initX) {
            this.initX = initX;
            return this;
        }
        public VActorSpawnHelperBuilder setInitY(float initY) {
            this.initY = initY;
            return this;
        }
        public VActorSpawnHelperBuilder setHx(float hx) {
            this.hx = hx;
            return this;
        }
        public VActorSpawnHelperBuilder setHy(float hy) {
            this.hy = hy;
            return this;
        }
        public VActorSpawnHelperBuilder setCategory(short category) {
            this.category = category;
            return this;
        }
        public VActorSpawnHelperBuilder setMask(short mask) {
            this.mask = mask;
            return this;
        }
        public VActorSpawnHelperBuilder setFriction(float friction) {
            this.friction = friction;
            return this;
        }
        public VActorSpawnHelperBuilder setDensity(float density) {
            this.density = density;
            return this;
        }
        public VActorSpawnHelperBuilder setUserData(Object userData) {
            this.userData = userData;
            return this;
        }
        public VActorSpawnHelperBuilder setSensor(boolean sensor) {
            this.sensor = sensor;
            return this;
        }


        public VActorSpawnHelper build() {
            VActorSpawnHelper helper = new VActorSpawnHelper();
            helper.bodyType = bodyType;
            helper.initX = initX;
            helper.initY = initY;
            helper.hx = hx;
            helper.hy = hy;
            helper.category = category;
            helper.mask = mask;
            helper.friction = friction;
            helper.density = density;
            helper.userData = userData;
            helper.sensor = sensor;
            return helper;
        }
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
    }

    public BodyDef.BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyDef.BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public short getCategory() {
        return category;
    }

    public void setCategory(short category) {
        this.category = category;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public float getHx() {
        return hx;
    }

    public void setHx(float hx) {
        this.hx = hx;
    }

    public float getHy() {
        return hy;
    }

    public void setHy(float hy) {
        this.hy = hy;
    }

    public float getInitX() {
        return initX;
    }

    public void setInitX(float initX) {
        this.initX = initX;
    }

    public float getInitY() {
        return initY;
    }

    public void setInitY(float initY) {
        this.initY = initY;
    }

    public short getMask() {
        return mask;
    }

    public void setMask(short mask) {
        this.mask = mask;
    }
}

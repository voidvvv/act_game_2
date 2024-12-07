package com.voidvvv.game.base;

public class Camp {
    public static final long NEUTRAL = 1;
    public static final long NEGATIVE = 1 << 1;
    public static final long POSITIVE = 1 << 2;

    public long campBit;

    public boolean compatible(Camp other) {
        return (this.campBit & other.campBit) != 0;
    }

    public void set(Camp other) {
        this.campBit = other.campBit;
    }

    public void reset() {
        this.campBit = 0;
    }
}

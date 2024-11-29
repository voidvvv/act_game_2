package com.voidvvv.game.base.buff;

import com.voidvvv.game.base.VActor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Buff {
    protected VActor owner;

    public int type() {
        return 0;
    };

    public abstract BuffOrder getOrder();

    public abstract void enter() ;

    public abstract void update();

    public abstract void exit();
}

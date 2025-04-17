package com.voidvvv.game.base.buff;

import com.badlogic.gdx.utils.Pool;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.utils.ReflectUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public abstract class Buff implements Pool.Poolable {
    protected VActor owner;
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract boolean expire();

    public abstract void merge(Buff other);

    public int type() {
        return 0;
    };

    public abstract BuffOrder getOrder();

    public abstract void enter() ;

    public abstract void update();

    public abstract void exit();

    @Override
    public boolean equals(Object obj) {
        Buff other = ReflectUtil.cast(obj, Buff.class);
        return other !=null && getId() == other.getId();
    }

    @Override
    public void reset() {
        exit();
        this.owner = null;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

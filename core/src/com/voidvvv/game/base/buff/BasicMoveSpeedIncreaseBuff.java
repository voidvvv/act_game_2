package com.voidvvv.game.base.buff;

import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.utils.ReflectUtil;

public class BasicMoveSpeedIncreaseBuff extends ExpireBuff{
    public float moveSpeedAdd = 0f;

    @Override
    public BuffOrder getOrder() {
        return BuffOrder.ATTR_PLUS;
    }

    @Override
    public void enter() {
        VCharacter cast = ReflectUtil.cast(owner, VCharacter.class);
        if (cast == null) {
            return;
        }
        cast.getBattleComponent().battleDirty = true;
        cast.getBattleComponent().getMoveSpeedTrans().add += this.moveSpeedAdd;
        cast.getBattleComponent().getMoveSpeedTrans().dirty = true;

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void exit() {
        VCharacter cast = ReflectUtil.cast(owner, VCharacter.class);
        if (cast == null) {
            return;
        }
        cast.getBattleComponent().battleDirty = true;
        cast.getBattleComponent().getMoveSpeedTrans().add -= this.moveSpeedAdd;
        cast.getBattleComponent().getMoveSpeedTrans().dirty = true;

    }

    @Override
    public void reset() {
        exit();
        this.owner = null;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

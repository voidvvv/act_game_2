package com.voidvvv.game.base.buff;

import com.badlogic.gdx.Gdx;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.utils.ReflectUtil;

public class BasicAttrIncreaseBuff extends ExpireBuff{

    public float attackAdd = 0f;

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
        cast.getBattleComponent().getAttackTrans().add += this.attackAdd;
        cast.getBattleComponent().getAttackTrans().dirty = true;
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
        cast.getBattleComponent().getAttackTrans().add -= this.attackAdd;
        cast.getBattleComponent().getAttackTrans().dirty = true;

    }

    @Override
    public void reset() {
        exit();
        this.owner = null;
    }
}

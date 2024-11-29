package com.mygdx.game.test.imgui.actor;

import com.badlogic.gdx.utils.Pools;
import com.mygdx.game.test.imgui.UIRender;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.buff.BasicMoveSpeedIncreaseBuff;
import com.voidvvv.game.base.buff.Buff;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.utils.ReflectUtil;
import imgui.ImGui;
import imgui.type.ImFloat;

import java.util.HashSet;
import java.util.Set;

public class ProtagnizeAttr implements UIRender {
    ImFloat speed = new ImFloat();
    @Override
    public void render() {
        VActor protagonist = ActGame.gameInstance().currentWorld().getProtagonist();
        VCharacter character = ReflectUtil.cast(protagonist, VCharacter.class);


        if (character != null) {
            boolean flag = false;
            BattleAttr attr = character.getActualBattleAttr();
            if (ImGui.begin("主角属性")){
                speed.set(attr.moveSpeed);
                ImGui.labelText("速度", speed + "");
                speed.set(attr.magicSpeed);
                flag = ImGui.inputFloat("释放速度",speed,100f);
                if (flag) {
                    attr.magicSpeed = speed.get();
                }
                ImGui.text("当前速度:  " + character.velocity.x + " - " + character.velocity.y);
                float totalGameTime = ActGame.gameInstance().totalGameTime;
                ImGui.text("时间: " + (int)totalGameTime);
            }
            flag = ImGui.button("施加移速buff 10秒钟");
            if (flag) {
                Buff buff = makeMoveSpeedBuff(10f, 200);
                character.getBuffComponent().add(buff);
            }
            ImGui.end();
        }
    }

    public static final int TEST_MOVE_SPEED_ID = -1;

    Set<Buff> testSet = new HashSet<>();
    public Buff makeMoveSpeedBuff (float time, float speedAdd) {
        BasicMoveSpeedIncreaseBuff buff = Pools.obtain(BasicMoveSpeedIncreaseBuff.class);
        buff.time = time;
        buff.moveSpeedAdd = speedAdd;
        buff.setId(TEST_MOVE_SPEED_ID);
        return buff;
    }
}

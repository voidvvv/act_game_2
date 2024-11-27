package com.mygdx.game.test.imgui.actor;

import com.mygdx.game.test.imgui.UIRender;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.utils.ReflectUtil;
import imgui.ImGui;
import imgui.type.ImFloat;

public class ProtagnizeAttr implements UIRender {
    ImFloat speed = new ImFloat();
    @Override
    public void render() {
        VActor protagonist = ActGame.gameInstance().currentWorld().getProtagonist();
        VCharacter character = ReflectUtil.cast(protagonist, VCharacter.class);


        if (character != null) {
            BattleAttr attr = character.getActualBattleAttr();
            if (ImGui.begin("主角属性")){
                speed.set(attr.moveSpeed);
                boolean flag = ImGui.inputFloat("速度", speed,500f);
                if (flag) {
                    attr.moveSpeed = speed.get();
                }
                speed.set(attr.magicSpeed);
                flag = ImGui.inputFloat("释放速度",speed,100f);
                if (flag) {
                    attr.magicSpeed = speed.get();
                }
                ImGui.text(character.velocity.x + " - " + character.velocity.y);
                float totalGameTime = ActGame.gameInstance().totalGameTime;
                ImGui.text((int)totalGameTime + "");
            }
            ImGui.end();
        }
    }
}

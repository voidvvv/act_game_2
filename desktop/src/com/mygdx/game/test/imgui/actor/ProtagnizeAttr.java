package com.mygdx.game.test.imgui.actor;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.utils.Pools;
import com.mygdx.game.test.imgui.UIRender;
import com.voidvvv.game.ActGame;
import com.voidvvv.game.base.VActor;
import com.voidvvv.game.base.VCharacter;
import com.voidvvv.game.base.buff.BasicAttrIncreaseBuff;
import com.voidvvv.game.base.buff.BasicMoveSpeedIncreaseBuff;
import com.voidvvv.game.base.buff.Buff;
import com.voidvvv.game.base.state.VCharactorStatus;
import com.voidvvv.game.battle.BattleAttr;
import com.voidvvv.game.context.world.VActWorld;
import com.voidvvv.game.context.world.VWorld;
import com.voidvvv.game.context.world.VWorldContextScreen;
import com.voidvvv.game.screen.test.AddSlimeTest;
import com.voidvvv.game.utils.ReflectUtil;
import imgui.ImGui;
import imgui.type.ImFloat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProtagnizeAttr implements UIRender {
    ImFloat speed = new ImFloat();
    @Override
    public void render() {
        VActor protagonist = null;
        Screen screen = ActGame.gameInstance().getScreen();
        VWorld world = null;
        if (screen != null && VWorldContextScreen.class.isAssignableFrom(screen.getClass())) {
            world = ((VWorldContextScreen) screen).getWorldContext().getWorld();
            if (world != null && VActWorld.class.isAssignableFrom(world.getClass())) {
                protagonist = ((VActWorld)world).getProtagonist();
            }
        }
        if (protagonist == null) {
            return;
        }
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
                ImGui.text("状态: " + character.getStateMachine().getCurrentState().getClass().getSimpleName());
            }
            flag = ImGui.button("施加移速buff 10秒钟");
            if (flag) {
                Buff buff = makeMoveSpeedBuff(10f, 200);
                character.getBuffComponent().add(buff);
            }
            flag = ImGui.button("施加攻击路buff 10秒钟");
            if (flag) {
                Buff buff = makeAttackUpBuff(10f, 1000);
                character.getBuffComponent().add(buff);
            }

            boolean addSlime = ImGui.button("addSlime");
            if(addSlime) {
                MessageManager.getInstance().dispatchMessage(AddSlimeTest.PRE_ADD_SLIME);
            }

            List<VActor> vActors = world.allActors();
            for (VActor a : vActors) {
                VCharacter c = ReflectUtil.cast(a, VCharacter.class);
                if (c != null) {
                    StateMachine<VCharacter, VCharactorStatus> stateMachine = c.getStateMachine();
                    String stateDes = "";
                    if (stateMachine == null) {
                        stateDes = "null !";
                    } else {
                        stateDes = c.getStateMachine().getCurrentState().getClass().getSimpleName();
                    }
                    ImGui.text(a.getName() + " _ " + stateDes +  " __ " + c.statusTime);
                }
            }

            ImGui.end();
        }
    }

    public static final int TEST_MOVE_SPEED_ID = -1;
    public static final int TEST_ATTACK_UP_ID = -2;

    public Buff makeMoveSpeedBuff (float time, float speedAdd) {
        BasicMoveSpeedIncreaseBuff buff = Pools.obtain(BasicMoveSpeedIncreaseBuff.class);
        buff.time = time;
        buff.moveSpeedAdd = speedAdd;
        buff.setId(TEST_MOVE_SPEED_ID);
        return buff;
    }

    public Buff makeAttackUpBuff (float time, float attackUp) {
        BasicAttrIncreaseBuff buff = Pools.obtain(BasicAttrIncreaseBuff.class);
        buff.time = time;
        buff.attackAdd = attackUp;
        buff.setId(TEST_ATTACK_UP_ID);
        return buff;
    }

}

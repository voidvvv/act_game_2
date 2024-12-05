//package com.voidvvv.game.base.skill.base;
//
//import com.voidvvv.game.ActGame;
//import com.voidvvv.game.VActorAdaptor;
//import com.voidvvv.game.base.VActor;
//import com.voidvvv.game.base.VCharacter;
//import com.voidvvv.game.base.skill.Cost;
//import com.voidvvv.game.base.skill.Skill;
//import com.voidvvv.game.utils.ReflectUtil;
//
//import java.util.HashSet;
//import java.util.Set;
//
//
//public class Hit implements Skill {
//    Cost cost = new Cost();
//
//    VCharacter owner;
//    HitListener listener = new HitListener();
//    float process = 0;
//    @Override
//    public int type() {
//        return 0;
//    }
//
//    @Override
//    public float percentage() {
//        return process;
//    }
//
//    @Override
//    public int init(VCharacter character) {
//        this.setOwner(character);
//        ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage(character.getName() + " 使用了撞击!");
//        return 0;
//    }
//
//    @Override
//    public VCharacter owner() {
//        return owner;
//    }
//
//    @Override
//    public void setOwner(VCharacter owner) {
//        this.owner = owner;
//    }
//
//    @Override
//    public boolean couldBeReplace(Skill skill) {
//        return false;
//    }
//
//    @Override
//    public void start() {
//        ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("撞击技能开始，获得霸体，并且进入冲撞状态");
//        owner.getListenerComponent().add(listener);
//        ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("并且给owner添加撞击技能的监听");
//
//    }
//
//    @Override
//    public void process(float delta) {
//        updateProcess(delta);
//        if (isEnding()) {
//            ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage("back to normal");
//        }
//    }
//
//    private void updateProcess(float delta) {
//        process += delta;
//    }
//
//    @Override
//    public void end() {
//        // 结束时，给owner移除冲撞监听器
//        owner.getListenerComponent().remove(listener);
//        owner = null;
//    }
//
//    @Override
//    public boolean isEnding() {
//        return process >= 1f;
//    }
//
//
//    @Override
//    public Cost cost() {
//        return cost;
//    }
//
//    @Override
//    public void onMove() {
//
//    }
//
//    @Override
//    public void reset() {
//        process = 0f;
//        listener.reset();
//        this.end();
//    }
//
//    class HitListener extends VActorAdaptor {
//        Set<VActor> actorSet = new HashSet<>();
//
//        @Override
//        public void afterHitOnActor() {
//            VActor lastHitActor = owner.lastHitActor;
//            VCharacter character = ReflectUtil.cast(lastHitActor, VCharacter.class);
//            if (character == null) {
//                return;
//            }
//            boolean add = actorSet.add(character);
//            if (add) {
//                // generate an event try to damage the character
//                ActGame.gameInstance().getSystemNotifyMessageManager().pushMessage(owner.getName() + " 的  冲撞技能命中了: " + character.getName());
//            }
//        }
//
//        @Override
//        public void reset() {
//            actorSet.clear();
//        }
//    }
//}

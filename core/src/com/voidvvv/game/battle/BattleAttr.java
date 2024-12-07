package com.voidvvv.game.battle;


import jdk.internal.reflect.Reflection;

public class BattleAttr {
    public float moveSpeed = 75f;
    public float attackSpeed = 100f;
    public float magicSpeed = 100f;


    public float attack;
    public float defense;
    public float magicStrength;

    public float maxHp = 1;
    public float maxMp = 1f;

    public float hp;
    public float mp;


    public void copyFrom(BattleAttr battleAttr) {
        moveSpeed = battleAttr.moveSpeed;
        attackSpeed = battleAttr.attackSpeed;
        magicSpeed = battleAttr.magicSpeed;

        attack = battleAttr.attack;
        defense = battleAttr.defense;
        magicStrength = battleAttr.magicStrength;
        maxHp = battleAttr.maxHp;
        maxMp = battleAttr.maxMp;
        hp = battleAttr.hp;
        mp = battleAttr.mp;
    }
}

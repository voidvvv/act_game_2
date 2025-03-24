package com.voidvvv.game.base.actors;

public class ActorConstants {
    public static final int DEFENCE_FIELD;
    public static final int ATTACK_FIELD;

    public static final int HP_FIELD;
    public static final int MP_FIELD;
    public static final int MOVE_SPEED_FIELD;
    public static final int ATTACK_SPEED_FIELD;
    public static final int SPELL_SPEED_FIELD;


    public static final int STATUS_IDLE;
    public static final int STATUS_WALKING;
    public static final int STATUS_SPELLING_01;
    public static final int STATUS_SPELLING_02;
    public static final int STATUS_ATTACK_01;

    public static final int MSG_RESET_ACTOR;
    public static final int MSG_ACTOR_BASE_VELOCITY_CHANGE;
    public static final int MSG_ACTOR_AFTER_BE_HIT;
    public static final int MSG_ACTOR_PRE_ATTEMPT_TO_SPELL;
    public static final int MSG_ACTOR_ATTEMPT_TO_SPELL;
    public static final int MSG_ACTOR_AFTER_BE_DAMAGED;
    public static final int MSG_ACTOR_AFTER_BE_ATTACK;
    public static final int MSG_ACTOR_AFTER_DYING;
    public static final int EXIST_CURRENT_PROCESS;



    public static final int ACTOR_TYPE_CHARACTER;
    public static final int ACTOR_TYPE_BULLET;
    public static final int ACTOR_TYPE_OBSTACLE;

    static {
        int s = 1;
        DEFENCE_FIELD = s++;
        ATTACK_FIELD = s++;
        HP_FIELD = s++;
        MP_FIELD = s++;
        MOVE_SPEED_FIELD = s++;
        ATTACK_SPEED_FIELD = s++;
        SPELL_SPEED_FIELD = s++;
        STATUS_IDLE = s++;
        STATUS_WALKING = s++;
        STATUS_SPELLING_01 = s++;
        STATUS_SPELLING_02 = s++;
        STATUS_ATTACK_01 = s++;

        MSG_RESET_ACTOR = s++;
        MSG_ACTOR_BASE_VELOCITY_CHANGE = s++;
        MSG_ACTOR_AFTER_BE_HIT = s++;
        MSG_ACTOR_PRE_ATTEMPT_TO_SPELL = s++;
        MSG_ACTOR_ATTEMPT_TO_SPELL = s++;
        MSG_ACTOR_AFTER_BE_DAMAGED = s++;
        MSG_ACTOR_AFTER_BE_ATTACK = s++;
        MSG_ACTOR_AFTER_DYING = s++;
        EXIST_CURRENT_PROCESS = s++;

        ACTOR_TYPE_CHARACTER = s++;
        ACTOR_TYPE_BULLET = s++;
        ACTOR_TYPE_OBSTACLE = s++;
    }


}
